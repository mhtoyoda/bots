package com.fiveware.io;


import com.fiveware.messaging.BrokerManager;
import com.fiveware.messaging.Producer;
import com.fiveware.model.*;
import com.fiveware.model.message.MessageBot;
import com.fiveware.model.message.MessageHeader;
import com.fiveware.model.message.MessageTask;
import com.fiveware.service.ServiceAgent;
import com.fiveware.task.StatusProcessItemTaskEnum;
import com.fiveware.task.StatusProcessTaskEnum;
import com.fiveware.task.TaskManager;
import com.fiveware.util.FileUtil;
import com.fiveware.validate.RuleValidations;
import com.fiveware.validate.ValidationFileErrorException;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Created by valdisnei on 13/06/17.
 */
@Component
public class ReadInputFile {
	
	private static Logger logger = LoggerFactory.getLogger(ReadInputFile.class);
	
    @Autowired
    private FileUtil fileUtil;

    @Autowired
    @Qualifier("eventBotProducer")
    private Producer<MessageBot> producer;

    @Autowired
    @Qualifier("eventTaskProducer")
    private Producer<MessageTask> taskProducer;
    
    @Autowired
    private TaskManager taskManager;

    @Autowired
    private BrokerManager brokerManager;

    @Autowired
    private RuleValidations ruleValidations;
    
    @Autowired
    private ServiceAgent serviceAgent;

    private Long userId = 1L;
    
    public void readFile(final String nameBot, final String path, InputStream file,
                         DeferredResult<ResponseEntity<String>> resultado) throws IOException {
        Task task = createTask(nameBot, userId);
        Bot bot = task.getBot();
        String separatorFile = bot.getSeparatorFile();
        Iterable<String> split = Splitter.on(separatorFile).split(bot.getFieldsInput());
        String[] fields = Iterables.toArray(split, String.class);
        List<String> lines = fileUtil.linesFrom(file);

        // Devolve a thread do Browser
        resultado.setResult(ResponseEntity.ok().body("OK"));

        try {
            taskManager.updateTask(task.getId(), StatusProcessTaskEnum.VALIDATING);

            ruleValidations.executeValidations(lines, fields, separatorFile);

            taskManager.updateTask(task.getId(), StatusProcessTaskEnum.PROCESSING);
            List<Record> allLines = fileUtil.linesFrom(lines, fields, separatorFile);


            sendListToQueue(task, allLines, path, separatorFile);
        } catch (ValidationFileErrorException e) {
            taskManager.updateTask(task.getId(), StatusProcessTaskEnum.REJECTED);
        } catch (Exception e){
        	logger.error("Error {}", e.getMessage());
        }
    }

    private void sendListToQueue(Task task, List<Record> recordList, String path, String separatorFile) {
        final List<String> lines = Lists.newArrayList();
        String queueName = String.format("%s.%s.IN", task.getBot().getNameBot(), task.getId());
        createQueueTaskIn(queueName);
        Consumer<List<Record>> consumer = records ->
                records
                        .stream()
                        .map(Record::getRecordMap)
                        .collect(Collectors.toList())
                        .stream().map(Map::values).forEach(convertMapToCSVLine(lines, separatorFile));
        consumer.accept(recordList);

        MessageHeader messageHeader = new MessageHeader
                .MessageHeaderBuilder(path)
                .timeStamp(System.currentTimeMillis())
                .build();

        if(CollectionUtils.isEmpty(lines)){
            ItemTask itemTask = taskManager.createItemTask(task, "");
            MessageBot messageBot = new MessageBot(task.getId(), itemTask.getId(), "", messageHeader);
            producer.send(queueName, messageBot);
            taskManager.updateItemTask(itemTask.getId(), StatusProcessItemTaskEnum.INLINE);
        }

        lines.stream().forEach(line -> {
            ItemTask itemTask = taskManager.createItemTask(task, line);
            MessageBot messageBot = new MessageBot(task.getId(), itemTask.getId(), line, messageHeader);
            producer.send(queueName, messageBot);
            taskManager.updateItemTask(itemTask.getId(), StatusProcessItemTaskEnum.INLINE);
        });
        
        sendNotificationTaskCreated(queueName, task.getBot().getNameBot());
    }

    private Consumer<Collection<Object>> convertMapToCSVLine(List<String> lines, String separatorFile) {
        return line -> {
            final StringJoiner joiner = new StringJoiner(separatorFile);
            line.forEach((column) -> joiner.add((CharSequence) column));
            lines.add(joiner.toString());
        };
    }

    private Task createTask(String nameBot, Long userId){
        return taskManager.createTask(nameBot, userId);
    }

    private void createQueueTaskIn(String queueName){
        brokerManager.createQueue(queueName);
    }
    
    private void sendNotificationTaskCreated(String nameQueueTask, String botName){ 
    	List<String> agents = getAgentsByBot(botName);
    	MessageTask message = new MessageTask(nameQueueTask, botName, agents);
		taskProducer.send("", message);
    }
    
    private List<String> getAgentsByBot(String botName){
    	//TODO pegar quantidade de parametros com credential por bot
    	List<Agent> agents = serviceAgent.findAgentsByBotName(botName);
    	if(CollectionUtils.isNotEmpty(agents)){
    		List<String> list = agents.stream().map(Agent::getNameAgent).collect(Collectors.toList());
    		return list;
    	}
    	return Lists.newArrayList();
    }
}