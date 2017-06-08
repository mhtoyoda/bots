package com.fiveware.messaging;

import java.util.Objects;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageReceiver implements Receiver<String> {
	
	@Autowired
	private RabbitTemplate rabbit;
	
	@Override
	public String receive(QueueName queueName){	
		Message text = rabbit.receive(queueName.name());
		String message = Objects.isNull(text) ? "" : new String(text.getBody());		
		return message;
	}
}