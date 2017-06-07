package com.fiveware.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("stopAgentMessage")
public class StopAgentMessage implements ConsumerTypeMessage {

	private static Logger log = LoggerFactory.getLogger(StopAgentMessage.class);
	
	@Override
	public void process(String message) {
		log.info("Message Receive Stop Agent {}",message);
	}

}
