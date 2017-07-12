package com.fiveware.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fiveware.messaging.ConsumerTypeMessage;
import com.fiveware.model.MessageAgent;

@Component("PAUSE_JOB")
public class PauseJobMessage implements ConsumerTypeMessage<MessageAgent> {

	private static Logger log = LoggerFactory.getLogger(PauseJobMessage.class);
	
	@Override
	public void process(MessageAgent message) {
		log.debug("Message Receive {}",message.toString());
	}

}
