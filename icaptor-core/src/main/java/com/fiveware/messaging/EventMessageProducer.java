package com.fiveware.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fiveware.model.message.MessageAgent;


@Service("eventMessageProducer")
public class EventMessageProducer implements Producer<MessageAgent> {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Override
	public void send(String queue, MessageAgent message){
		rabbitTemplate.convertAndSend(queue, message);
	}
}
