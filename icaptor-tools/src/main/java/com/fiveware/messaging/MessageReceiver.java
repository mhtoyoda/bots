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
	public String receive(TypeMessage typeMessage){	
		log.info("Receive message type {}",typeMessage.name());
		Message text = rabbit.receive(typeMessage.name());
		return new String(text.getBody());
	}
}
