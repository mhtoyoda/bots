package com.fiveware.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fiveware.model.MessageAgent;

@Component("START_AGENT")
public class InputDictionaryMessage implements ConsumerTypeMessage {

	private static Logger log = LoggerFactory.getLogger(StartAgentMessage.class);
	
	@Override
	public void process(MessageAgent message) {
		log.info("Message Receive {}",message.toString());
	}

}
