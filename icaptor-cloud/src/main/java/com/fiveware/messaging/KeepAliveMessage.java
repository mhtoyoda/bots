package com.fiveware.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fiveware.model.MessageAgent;

@Component("keepAliveMessage")
public class KeepAliveMessage implements ConsumerTypeMessage {

	private static Logger log = LoggerFactory.getLogger(KeepAliveMessage.class);
	
	@Override
	public void process(MessageAgent message) {
		log.info("Message Receive {}",message.toString());
	}

}
