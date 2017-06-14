package com.fiveware.scheduler;

import java.util.Map;
import java.util.Objects;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fiveware.messaging.ConsumerTypeMessage;
import com.fiveware.messaging.Receiver;
import com.fiveware.messaging.TypeConsumerMessage;
import com.fiveware.messaging.TypeMessage;
import com.fiveware.model.MessageAgent;
import com.fiveware.model.MessageInputDictionary;

@Component
public class EventsConsumerScheduler {

	private Map<String, ConsumerTypeMessage> consumersMap;
	
	@Autowired
	@Qualifier("eventMessageReceiver")
	private Receiver<MessageAgent> receiver;


	@Autowired
	@Qualifier("eventInputDictionaryReceiver")
	private Receiver<MessageInputDictionary> receiver2;


	@Autowired
	private TypeConsumerMessage typeConsumerMessage;
	
	@PostConstruct
	public void init(){		
		consumersMap = typeConsumerMessage.getConsumer("com.fiveware.messaging");
	}
	
	@Scheduled(fixedDelay = 10000)
	public void execute() {
		MessageAgent messageAgent = receiver.receive();
		if(!Objects.isNull(messageAgent)){
			consumersMap.get(messageAgent.getTypeMessage().name()).process(messageAgent);
		}

		MessageInputDictionary dictionaryMessage = receiver2.receive();
		if(!Objects.isNull(dictionaryMessage))
			consumersMap.get(TypeMessage.INPUT_DICTIONARY.name()).process(dictionaryMessage);
	}
}