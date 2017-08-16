package com.fiveware.scheduler;

import java.util.Map;
import java.util.Objects;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fiveware.messaging.ConsumerTypeMessage;
import com.fiveware.messaging.QueueName;
import com.fiveware.messaging.Receiver;
import com.fiveware.messaging.TypeConsumerMessage;
import com.fiveware.model.message.MessageAgent;

@Component
public class EventsConsumerScheduler {

	@Autowired
	private Map<String, ConsumerTypeMessage> consumersMap;
	
	@Autowired
	@Qualifier("eventMessageReceiver")
	private Receiver<MessageAgent> receiver;

	@Autowired
	private TypeConsumerMessage typeConsumerMessage;
	
	@PostConstruct
	public void init(){		
		consumersMap = typeConsumerMessage.loadConsumer();
	}
	
	@Scheduled(fixedDelay = 2000)
	public void execute() {
		MessageAgent messageAgent = receiver.receive(QueueName.EVENTS.name());
		if(!Objects.isNull(messageAgent)){
			consumersMap.get(messageAgent.getTypeMessage().name()).process(messageAgent);
		}
	}
}