package com.fiveware.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fiveware.model.MessageAgent;

@Component("STOP_AGENT")
public class StopAgentMessage implements ConsumerTypeMessage {

	private static Logger log = LoggerFactory.getLogger(StopAgentMessage.class);
	
	@Override
	public void process(MessageAgent message) {
		log.info("Message Receive {}",message.toString());
	}

}
