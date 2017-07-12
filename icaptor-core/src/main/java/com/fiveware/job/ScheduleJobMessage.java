package com.fiveware.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fiveware.messaging.ConsumerTypeMessage;
import com.fiveware.model.MessageAgent;

@Component("SCHEDULE_JOB")
public class ScheduleJobMessage implements ConsumerTypeMessage<MessageAgent> {

	private static Logger log = LoggerFactory.getLogger(ScheduleJobMessage.class);
	
	@Override
	public void process(MessageAgent message) {
		log.debug("Message Receive {}",message.toString());
	}

}
