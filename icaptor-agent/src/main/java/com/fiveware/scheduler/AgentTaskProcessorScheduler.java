package com.fiveware.scheduler;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fiveware.config.agent.AgentConfigProperties;
import com.fiveware.context.QueueContext;
import com.fiveware.messaging.Receiver;
import com.fiveware.model.Bot;
import com.fiveware.model.message.MessageTask;
import com.fiveware.service.ServiceAgent;

@Component
public class AgentTaskProcessorScheduler {

	private static Logger log = LoggerFactory.getLogger(AgentTaskProcessorScheduler.class);
	
	@Autowired
	private QueueContext queueContext;

	@Autowired
	private AgentConfigProperties data;
	
	@Autowired
	@Qualifier("eventTaskReceiver")
	private Receiver<MessageTask> receiver;
	
	@Autowired
	private ServiceAgent serviceAgent;
	
	@Scheduled(fixedDelayString = "${broker.queue.send.schedularTime}")
	public void process() {
		String queueName = String.format("tasks.%s.in", StringUtils.trim(data.getAgentName()));
		List<Bot> bots = serviceAgent.findBotsByAgent(data.getAgentName());
		bots.forEach(bot -> {
			String botName = bot.getNameBot();								
			Optional<MessageTask> obj = receiveMessage(queueName);
			obj.ifPresent((MessageTask message) -> {
				processMessage(botName, message);
			});
		});
	}

	public void processMessage(String botName, MessageTask obj){
		if(botName.equals(obj.getBotName())){
			queueContext.addQueueInContext(botName, obj.getNameQueueTask());
			log.info("Add queue task -> {} - bot -> {}", obj.getNameQueueTask(), obj.getBotName());
		}
	}

	public Optional<MessageTask> receiveMessage(String queueName) {
		return Optional.ofNullable(receiver.receive(queueName));
	}
}