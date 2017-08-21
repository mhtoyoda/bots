package com.fiveware.messaging;

import com.fiveware.model.message.MessageAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fiveware.model.message.MessageParameterAgent;

@Component("PARAMETERS")
public class CloudParameterMessage implements ConsumerTypeMessage<MessageParameterAgent> {

	private static Logger logger = LoggerFactory.getLogger(CloudParameterMessage.class);
	
	@Autowired
	@Qualifier("eventMessageProducer")
	private Producer<MessageAgent> producer;

	@Override
	public void process(MessageParameterAgent message) {
		logger.info("Request Parameters Agent: {}", message.getAgent());
		
	}
}
