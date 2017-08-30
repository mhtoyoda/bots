package com.fiveware.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fiveware.model.message.MessageParameterAgent;


@Service("parameterMessageProducer")
public class ParameterMessageProducer implements Producer<MessageParameterAgent> {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Override
	public void send(String queue, MessageParameterAgent message){
		rabbitTemplate.convertAndSend(queue, message);
	}
}
