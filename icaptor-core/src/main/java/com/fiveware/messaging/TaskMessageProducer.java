package com.fiveware.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fiveware.model.MessageBot;


@Service("taskMessageProducer")
public class TaskMessageProducer implements Producer<MessageBot> {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Override
	public void send(String queue, MessageBot message){
		rabbitTemplate.convertAndSend(queue, message);
	}
}
