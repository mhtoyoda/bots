package com.fiveware.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageReceiver implements Receiver<String> {
	
	private static final Logger log = LoggerFactory.getLogger(MessageReceiver.class);
	
	@Autowired
	private RabbitTemplate rabbit;
	
	@Override
	public void receive(TypeMessage typeMessage){		
		Message text = rabbit.receive(typeMessage.name());
		log.info("Message Receive: {}", new String(text.getBody()));
	}
}
