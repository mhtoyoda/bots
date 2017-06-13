package com.fiveware.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fiveware.model.MessageAgent;

@Component("KEEP_ALIVE")
public class KeepAliveMessage implements ConsumerTypeMessage<MessageAgent> {

	private static Logger log = LoggerFactory.getLogger(KeepAliveMessage.class);
	
	@Override
	public void process(MessageAgent message) {
		log.info("Message Receive {}",message.toString());
	}

}
