package com.fiveware.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fiveware.messaging.ConsumerTypeMessage;
import com.fiveware.model.MessageAgent;

@Component("START_JOB")
public class StartTaskMessage implements ConsumerTypeMessage<MessageAgent> {

	private static Logger log = LoggerFactory.getLogger(StartTaskMessage.class);
	
	@Override
	public void process(MessageAgent message) {
		log.debug("Message Receive {}",message.toString());
	}

}
