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
		createQueueSimple(queueName+"_IN");
		createQueueSimple(queueName+"_OUT");
	}
	
	public void createQueueSimple(String queueName){
		Queue queue = declareQueue(queueName);		
		TopicExchange exchange = new TopicExchange("file-exchange", true, false);
		rabbitAdmin.declareExchange(exchange);		
		rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(queue.getName()));
	}
	
	private Queue declareQueue(String queueName) {
		Queue queue = new Queue(queueName, true);
		rabbitAdmin.declareQueue(queue);
		return queue;
	}
	
}
