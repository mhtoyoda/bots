package com.fiveware.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fiveware.messaging.Producer;
import com.fiveware.model.MessageBot;
import com.fiveware.model.MessageHeader;
import com.fiveware.model.Record;
import com.fiveware.task.TaskManager;
import com.fiveware.util.FileUtil;
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

    public void readFile(final String queueName, final String path, InputStream file) throws IOException {
        String[] fields = {"campo1"};
        List<Record> allLines = fileUtil.linesFrom(file, fields, "|");                	
        sendListToQueue(allLines, queueName, path, allLines.size());
    }

    private void sendListToQueue(List<Record> recordList, String queueName, String path, Integer size) {
    	final List<String> lines = Lists.newArrayList();

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
    	
    	lines.stream().forEach(line -> {
    		MessageBot messageBot = new MessageBot(1L, 1L, line, messageHeader);
    		producer.send(queueName, messageBot);    		
    	});
    }
    
    private Consumer<Collection<Object>> convertMapToCSVLine(List<String> lines) {
        return line -> {
            final StringJoiner joiner = new StringJoiner("|");
            line.forEach((column) -> joiner.add((CharSequence) column));
            lines.add(joiner.toString());
        };
    }
    
}