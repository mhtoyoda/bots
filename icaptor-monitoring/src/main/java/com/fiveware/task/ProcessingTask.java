package com.fiveware.task;

import com.fiveware.messaging.BrokerManager;
import com.fiveware.messaging.Producer;
import com.fiveware.model.*;
import com.fiveware.model.message.MessageBot;
import com.fiveware.model.message.MessageHeader;
import com.fiveware.model.message.MessageTask;
import com.fiveware.parameter.ParameterResolver;
import com.fiveware.service.ServiceAgent;
import com.fiveware.service.ServiceElasticSearch;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProcessingTask {

	static Logger logger = LoggerFactory.getLogger(ProcessingTask.class);

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

	@Value("${spring.profiles.active}")
	private String profile;

	@Autowired
	private ServiceElasticSearch serviceElasticSearch;

	public void applyUpdateTaskProcessing() {
		List<Task> tasks = taskManager.allTaskProcessing(StatusProcessTaskEnum.PROCESSING.getName());
		tasks.stream().forEach(task -> {
			String queueName = String.format("%s.%s.IN", task.getBot().getNameBot(), task.getId());
			logger.info("{}",task);
			if ("dev".equalsIgnoreCase(profile))
					serviceElasticSearch.log(task,task.getId());

			sentItemTaskToQueue(queueName, task);
		});
	}

	private void sentItemTaskToQueue(String queueName, Task task) {
		List<String> statusList = Lists.newArrayList(StatusProcessItemTaskEnum.AVAILABLE.getName());
		List<ItemTask> itemTaskList = taskManager.itemTaskListStatus(statusList, task.getId());
		MessageHeader messageHeader = new MessageHeader.MessageHeaderBuilder("")
														.timeStamp(System.currentTimeMillis())
														.build();
		
		if(CollectionUtils.isNotEmpty(itemTaskList)){
			createQueueTaskIn(queueName);
			itemTaskList.stream().forEach(itemTask -> {
				String line = StringUtils.defaultIfBlank(itemTask.getDataIn(), "");
				MessageBot messageBot = new MessageBot(task.getId(), itemTask.getId(), line, messageHeader);
				producer.send(queueName, messageBot);
				taskManager.updateItemTask(itemTask.getId(), StatusProcessItemTaskEnum.INLINE);
				if ("dev".equalsIgnoreCase(profile))
					serviceElasticSearch.log(itemTask,itemTask.getId());
			});
			
			sendNotificationTaskCreated(queueName, task.getBot().getNameBot());			
		}
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