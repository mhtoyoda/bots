package com.fiveware.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MessageProducer implements Producer<String> {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Override
	public void send(TypeMessage typeMessage, String message){
		rabbitTemplate.convertAndSend(typeMessage.name(), message);
	}
}
