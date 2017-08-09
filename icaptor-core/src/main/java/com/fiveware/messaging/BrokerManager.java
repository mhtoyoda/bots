package com.fiveware.messaging;

import org.springframework.amqp.core.AmqpManagementOperations;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BrokerManager {
	
	@Autowired
	private RabbitAdmin rabbitAdmin;
	
	@Autowired
	private AmqpManagementOperations amqpManagementOperations;
	
	public void createQueue(String queueName){
		if(notExistQueue(queueName)){
			Queue queue = declareQueue(queueName);		
			DirectExchange exchange = new DirectExchange("file-exchange", true, false);		
			rabbitAdmin.declareExchange(exchange);
			rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(queue.getName()));			
		}
	}
	
	private Queue declareQueue(String queueName) {
		Queue queue = new Queue(queueName, true);
		rabbitAdmin.declareQueue(queue);
		return queue;
	}
	
	public boolean deleteQueue(String queueName){
		return rabbitAdmin.deleteQueue(queueName);		
	}
	
	private boolean notExistQueue(String queue){
		List<Queue> queues = amqpManagementOperations.getQueues().stream()
				.filter(q -> queue.equals(q.getName()))
				.collect(Collectors.toList());
		return queues.isEmpty();
	}
}
