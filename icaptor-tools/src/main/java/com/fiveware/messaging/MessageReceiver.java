package com.fiveware.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageReceiver implements Receiver {
	
	private static final Logger log = LoggerFactory.getLogger(MessageReceiver.class);
	
	@Autowired
	private RabbitTemplate rabbit;
	
	@Autowired
	private Queue queue;
	
	@Override
	public void receive(String message){		
		Message text = rabbit.receive(queue.getName());
		log.info("Message Receive: {}", new String(text.getBody()));
	}
}
