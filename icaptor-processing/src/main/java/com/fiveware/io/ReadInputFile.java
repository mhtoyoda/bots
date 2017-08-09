package com.fiveware.io;

import com.fiveware.messaging.BrokerManager;
import com.fiveware.messaging.Producer;
import com.fiveware.model.*;
import com.fiveware.task.TaskManager;
import com.fiveware.util.FileUtil;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.StringJoiner;

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
    
    @Autowired
    private BrokerManager brokerManager;

	private Long userId = 1L;

    public void readFile(final String nameBot, final String path, InputStream file) throws IOException {
        Task task = createTask(nameBot, userId);
        Bot bot = task.getBot();
        String separatorFile = bot.getSeparatorFile();
		Iterable<String> split = Splitter.on(separatorFile).split(bot.getFieldsInput());
        String[] fields = Iterables.toArray(split, String.class);
		Record allLines = fileUtil.linesFrom(file, fields, separatorFile);

		//TODO incluir validacao de arquivo aqui
        sendListToQueue(task, allLines, path);
    }

    private void sendListToQueue(Task task, Record record, String path) {
    	String queueName = String.format("%s.%s.IN", task.getBot().getNameBot(), task.getId());
    	createQueueTaskIn(queueName);
        String line = convertMapToCSVLine(record, "|");
//
    	MessageHeader messageHeader = new MessageHeader
                .MessageHeaderBuilder(path)
                .timeStamp(System.currentTimeMillis())
                .build();

    	if (Strings.isNullOrEmpty(line)){
            ItemTask itemTask = taskManager.createItemTask(task, "");
            MessageBot messageBot = new MessageBot(task.getId(), itemTask.getId(), "", messageHeader);
            producer.send(queueName, messageBot);
        }

        ItemTask itemTask = taskManager.createItemTask(task, line);
        MessageBot messageBot = new MessageBot(task.getId(), itemTask.getId(), line, messageHeader);
        producer.send(queueName, messageBot);
    }
    

    private String convertMapToCSVLine(Record record,String separator) {
        final StringJoiner joiner = new StringJoiner(separator);
        record.getRecordMap().forEach((k,v)->{
            joiner.add((CharSequence) v);
        });

        return joiner.toString();
    }
    
    private Task createTask(String nameBot, Long userId){
    	return taskManager.createTask(nameBot, userId);
    }
 
    private void createQueueTaskIn(String queueName){
    	brokerManager.createQueue(queueName);
    }
}