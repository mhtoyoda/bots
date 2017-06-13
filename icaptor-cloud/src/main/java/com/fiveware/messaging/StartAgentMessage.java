package com.fiveware.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fiveware.integration.ServerAgentIntegration;
import com.fiveware.model.MessageAgent;

@Component("START_AGENT")
public class StartAgentMessage implements ConsumerTypeMessage<MessageAgent> {

	private static Logger log = LoggerFactory.getLogger(StartAgentMessage.class);
	
	@Autowired
	private ServerAgentIntegration serverAgentIntegration;
	
	@Override
	public void process(MessageAgent message) {
		log.info("Message Receive {}",message.toString());
		serverAgentIntegration.join(message);
	}

}
