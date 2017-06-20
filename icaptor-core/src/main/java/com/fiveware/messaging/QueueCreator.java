package com.fiveware.messaging;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueueCreator {
	
	@Autowired
	private RabbitAdmin rabbitAdmin;
	
	public void createQueue(String queueName){
		Queue queueIn = declareQueue(queueName+"_IN");
		Queue queueOut = declareQueue(queueName+"_OUT");
		
		TopicExchange exchange = new TopicExchange("file-exchange", true, false);
		rabbitAdmin.declareExchange(exchange);
		
		rabbitAdmin.declareBinding(BindingBuilder.bind(queueIn).to(exchange).with(queueIn.getName()));
		rabbitAdmin.declareBinding(BindingBuilder.bind(queueOut).to(exchange).with(queueOut.getName()));
	}

	private Queue declareQueue(String queueName) {
		Queue queue = new Queue(queueName, true);
		rabbitAdmin.declareQueue(queue);
		return queue;
	}
	
}
