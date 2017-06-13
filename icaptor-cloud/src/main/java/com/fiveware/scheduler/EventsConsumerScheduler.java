package com.fiveware.scheduler;

import java.util.Map;
import java.util.Objects;

import javax.annotation.PostConstruct;

import com.fiveware.messaging.*;
import com.fiveware.model.MessageInputDictionary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fiveware.model.MessageAgent;

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