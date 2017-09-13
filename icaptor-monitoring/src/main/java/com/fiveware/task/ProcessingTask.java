package com.fiveware.task;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fiveware.messaging.BrokerManager;
import com.fiveware.messaging.Producer;
import com.fiveware.model.Agent;
import com.fiveware.model.ItemTask;
import com.fiveware.model.Record;
import com.fiveware.model.StatusProcessItemTaskEnum;
import com.fiveware.model.StatusProcessTaskEnum;
import com.fiveware.model.Task;
import com.fiveware.model.message.MessageBot;
import com.fiveware.model.message.MessageHeader;
import com.fiveware.model.message.MessageTask;
import com.fiveware.parameter.ParameterResolver;
import com.fiveware.service.ServiceAgent;
import com.fiveware.util.CsvConvertUtil;
import com.google.common.collect.Lists;

@Component
public class ProcessingTask {

	@Autowired
	private CsvConvertUtil csvConvertUtil;

	@Autowired
	private TaskManager taskManager;

	@Autowired
	private BrokerManager brokerManager;
	
    @Autowired
    private ServiceAgent serviceAgent;

    @Autowired
    private ParameterResolver parameterResolver;
    
    @Autowired
    @Qualifier("eventBotProducer")
    private Producer<MessageBot> producer;
    
    @Autowired
    @Qualifier("eventTaskProducer")
    private Producer<MessageTask> taskProducer;
    
	public void applyUpdateTaskProcessing() {
		List<Task> tasks = taskManager.allTaskProcessing(StatusProcessTaskEnum.PROCESSING.getName());
		tasks.stream().forEach(task -> {
			String queueName = String.format("%s.%s.IN", task.getBot().getNameBot(), task.getId());
			createQueueTaskIn(queueName);
			sentItemTaskToQueue(queueName, task);
		});
	}

	private void sentItemTaskToQueue(String queueName, Task task) {
		List<String> statusList = Lists.newArrayList(StatusProcessItemTaskEnum.AVAILABLE.getName());
		List<ItemTask> itemTaskList = taskManager.itemTaskListStatus(statusList, task.getId());
		itemTaskList.stream().forEach(itemTask -> {
			
			MessageBot messageBot = new MessageBot(task.getId(), itemTask.getId(), line, messageHeader);
			producer.send(queueName, messageBot);
			taskManager.updateItemTask(itemTask.getId(), StatusProcessItemTaskEnum.INLINE);
		});
		taskManager.updateTask(task.getId(), StatusProcessTaskEnum.SUSPENDED);
	}

	private void sendListToQueue(Task task, List<Record> recordList, String path, String separatorFile) {
		final List<String> lines = Lists.newArrayList();
		String queueName = String.format("%s.%s.IN", task.getBot().getNameBot(), task.getId());		
		Consumer<List<Record>> consumer = records -> records.stream().map(Record::getRecordMap)
				.collect(Collectors.toList()).stream().map(Map::values)
				.forEach(csvConvertUtil.convertMapToCSVLine(lines, separatorFile));
		consumer.accept(recordList);

		MessageHeader messageHeader = new MessageHeader.MessageHeaderBuilder(path).timeStamp(System.currentTimeMillis())
				.build();

		if (CollectionUtils.isEmpty(lines)) {
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

	private void createQueueTaskIn(String queueName) {
		brokerManager.createQueue(queueName);
	}

	private void sendNotificationTaskCreated(String nameQueueTask, String botName) {
		List<String> agents = getAgentsByBot(botName);
		MessageTask message = new MessageTask(nameQueueTask, botName, agents);
		taskProducer.send("", message);
	}

	private List<String> getAgentsByBot(String botName) {
		List<Agent> agents = serviceAgent.findAgentsByBotName(botName);
		if (CollectionUtils.isNotEmpty(agents)) {
			if (parameterResolver.hasNecessaryParameterFromBot(botName)
					&& parameterResolver.exclusiveParameterCredential(botName)) {
				int countParameterCredential = parameterResolver.countParameterCredential(botName);
				agents = agents.stream().limit(countParameterCredential).collect(Collectors.toList());
			}
			List<String> list = agents.stream().map(Agent::getNameAgent).collect(Collectors.toList());
			return list;
		}
		return Lists.newArrayList();
	}
}
