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

import com.fiveware.bot.BotContext;
import com.fiveware.config.agent.AgentConfigProperties;
import com.fiveware.context.QueueContext;
import com.fiveware.messaging.Receiver;
import com.fiveware.model.Bot;
import com.fiveware.model.message.MessageTask;

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
	private BotContext botContext;
	
	@Scheduled(fixedDelayString = "${icaptor.broker.queue-send-schedular-time}")
	public void process() {
		String queueName = String.format("tasks.%s.in", StringUtils.trim(data.getAgentName()));
		List<Bot> bots = botContext.bots();
		Optional<MessageTask> obj = receiveMessage(queueName);
		obj.ifPresent((MessageTask message) -> {		
			processMessage(bots, message);
		});
	}
	
	private boolean containsBot(List<Bot> bots, String botName){		
		return bots.stream().filter(b -> b.getNameBot().equalsIgnoreCase(botName)).findFirst().isPresent();
	}

	public void processMessage(List<Bot> bots, MessageTask obj){
		String botName = obj.getBotName();
		if(containsBot(bots, botName)){
			queueContext.setKey(botName);
			queueContext.setKeyValue(data.getAgentName());
			obj.getAgents().forEach(agent -> {				
				queueContext.addQueueInContext(botName, agent, obj.getNameQueueTask());
			});
			log.info("Add queue task -> {} - bot -> {}", obj.getNameQueueTask(), obj.getBotName());
		}
	}

	public Optional<MessageTask> receiveMessage(String queueName) {
		return Optional.ofNullable(receiver.receive(queueName));
	}
}