package com.fiveware.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.StringJoiner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fiveware.messaging.BrokerManager;
import com.fiveware.messaging.Producer;
import com.fiveware.model.*;
import com.fiveware.task.StatuProcessEnum;
import com.fiveware.task.TaskManager;
import com.fiveware.util.FileUtil;
import com.fiveware.validate.RuleValidations;
import com.fiveware.validate.ValidationFileErrorException;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;

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
    
    @Autowired
    private RuleValidations ruleValidations;

	private Long userId = 1L;

    public void readFile(final String nameBot, final String path, InputStream file) throws IOException {
        Task task = createTask(nameBot, userId);
        Bot bot = task.getBot();
        String separatorFile = bot.getSeparatorFile();
		Iterable<String> split = Splitter.on(separatorFile).split(bot.getFieldsInput());
        String[] fields = Iterables.toArray(split, String.class);
        List<String> lines = fileUtil.linesFrom(file);
        try {
        	taskManager.updateTask(task.getId(), StatuProcessEnum.VALIDATING);
        	ruleValidations.executeValidations(lines, fields, separatorFile);
        	taskManager.updateTask(task.getId(), StatuProcessEnum.PROCESSING);
        	List<Record> allLines = fileUtil.linesFrom(lines, fields, separatorFile);
        	sendListToQueue(task, allLines, path);
		} catch (ValidationFileErrorException e) {			
			taskManager.updateTask(task.getId(), StatuProcessEnum.REJECTED);
		}
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