package com.fiveware.scheduler;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fiveware.messaging.ConsumerTypeMessage;
import com.fiveware.messaging.Receiver;
import com.fiveware.messaging.TypeMessage;
import com.fiveware.model.MessageAgent;

@Component
public class EventsConsumerScheduler {

	private Map<String, ConsumerTypeMessage> consumersMap;
	
	@Autowired
	@Qualifier("eventMessageReceiver")
	private Receiver<MessageAgent> receiver;
	
	@Autowired
	@Qualifier("startAgentMessage")
	private ConsumerTypeMessage startAgentMessage;
	
	@Autowired
	@Qualifier("keepAliveMessage")
	private ConsumerTypeMessage keepAliveMessage;
	
	@Autowired
	@Qualifier("stopAgentMessage")
	private ConsumerTypeMessage stopAgentMessage;
	
	@PostConstruct
	public void init(){		
		consumersMap = new ConcurrentHashMap<String, ConsumerTypeMessage>();
		consumersMap.put(TypeMessage.START_AGENT.name(), startAgentMessage);
		consumersMap.put(TypeMessage.KEEP_ALIVE.name(), keepAliveMessage);
		consumersMap.put(TypeMessage.STOP_AGENT.name(), stopAgentMessage);
	}
	
	@Scheduled(fixedDelay = 10000)
	public void execute() {
		MessageAgent messageAgent = receiver.receive();
		if(!Objects.isNull(messageAgent)){
			consumersMap.get(messageAgent.getTypeMessage().name()).process(messageAgent);
		}

	}
}