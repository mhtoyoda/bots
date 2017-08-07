package com.fiveware.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fiveware.messaging.Producer;
import com.fiveware.model.Bot;
import com.fiveware.model.ItemTask;
import com.fiveware.model.MessageBot;
import com.fiveware.model.MessageHeader;
import com.fiveware.model.Record;
import com.fiveware.model.Task;
import com.fiveware.task.TaskManager;
import com.fiveware.util.FileUtil;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * Created by valdisnei on 13/06/17.
 */
@Component
public class ReadInputFile {

    public static final int N_LINES_BY_AGENT = 2;
    
    @Autowired
    private FileUtil fileUtil;

    @Autowired
    @Qualifier("eventBotProducer")
    private Producer<MessageBot> producer;

    @Autowired
    private TaskManager taskManager;

	private Long userId = 1L;

    public void readFile(final String nameBot, final String path, InputStream file) throws IOException {
        Task task = createTask(nameBot, userId);
        Bot bot = task.getBot();
        String separatorFile = bot.getSeparatorFile();
		Iterable<String> split = Splitter.on(separatorFile).split(bot.getFieldsInput());
        String[] fields = Iterables.toArray(split, String.class);
		List<Record> allLines = fileUtil.linesFrom(file, fields, separatorFile);
		//TODO incluir validacao de arquivo aqui
        sendListToQueue(task, allLines, path, allLines.size());
    }

    private void sendListToQueue(Task task, List<Record> recordList, String path, Integer size) {
    	final List<String> lines = Lists.newArrayList();
    	String nameQueue = String.format("%s.%s.IN", task.getBot().getNameBot(), task.getId());
        Consumer<List<Record>> consumer = records ->
                records
                        .stream()
                        .map(Record::getRecordMap)
                        .collect(Collectors.toList())
                        .stream().map(Map::values).forEach(convertMapToCSVLine(lines));
        consumer.accept(recordList);
        
    	MessageHeader messageHeader = new MessageHeader
                .MessageHeaderBuilder(path, size)                
                .timeStamp(System.currentTimeMillis())
                .build();
    	
    	if(CollectionUtils.isEmpty(lines)){
    		ItemTask itemTask = taskManager.createItemTask(task, "");
    		MessageBot messageBot = new MessageBot(task.getId(), itemTask.getId(), "", messageHeader);
    		producer.send(nameQueue, messageBot);
    	}
    	
    	lines.stream().forEach(line -> {
    		ItemTask itemTask = taskManager.createItemTask(task, line);
    		MessageBot messageBot = new MessageBot(task.getId(), itemTask.getId(), line, messageHeader);
    		producer.send(nameQueue, messageBot);
    	});
    }
    
    private Consumer<Collection<Object>> convertMapToCSVLine(List<String> lines) {
        return line -> {
            final StringJoiner joiner = new StringJoiner("|");
            line.forEach((column) -> joiner.add((CharSequence) column));
            lines.add(joiner.toString());
        };
    }
    
    private Task createTask(String nameBot, Long userId){
    	return taskManager.createTask(nameBot, userId);
    }
 
}