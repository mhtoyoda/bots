package com.fiveware.messaging;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BrokerManager {
	
	private Logger log = LoggerFactory.getLogger(BrokerManager.class);
	
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
			log.info("Create queue: {}", queueName);
		}
	}
	
	private Queue declareQueue(String queueName) {
		Queue queue = new Queue(queueName, true);
		rabbitAdmin.declareQueue(queue);
		return queue;
	}
	
	public boolean deleteQueue(String queueName){
		log.info("Delete queue: {}", queueName);
		return rabbitAdmin.deleteQueue(queueName);
	}

	private boolean notExistQueue(String queue){
		List<Queue> queues = amqpManagementOperations.getQueues().stream()
				.filter(q -> queue.equals(q.getName()))
				.collect(Collectors.toList());
		return queues.isEmpty();
	}
	
	private boolean notExistExchange(String nameExchange){
		List<Exchange> exchanges = amqpManagementOperations.getExchanges().stream()
				.filter(e -> nameExchange.equals(e.getName()))
				.collect(Collectors.toList());
		return exchanges.isEmpty();
	}
	
	public void createTopicExchange(String exchangeName, List<String> agents){
		FanoutExchange exchange;
		if(notExistExchange(exchangeName)){
			exchange = new FanoutExchange(exchangeName, true, false);
			log.info("Create exchange: {}", exchangeName);
		}else{
			exchange = (FanoutExchange) amqpManagementOperations.getExchange(exchangeName);
		}
		rabbitAdmin.declareExchange(exchange);
		
		agents.stream().forEach(agent -> {
			String queueName = "tasks."+agent+".in";
			if(notExistQueue(queueName)){
				Queue queue = declareQueue(queueName);				
				rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(exchange));
				log.info("Create queue: {} - exchange: {}", queueName, exchangeName);
			}
		});
	}
	
	public void createTopicExchange(String exchangeName, String agent){
		FanoutExchange exchange;
		if(notExistExchange(exchangeName)){
			exchange = new FanoutExchange(exchangeName, true, false);
			rabbitAdmin.declareExchange(exchange);
			log.info("Create exchange: {}", exchangeName);
		}else{
			exchange = (FanoutExchange) amqpManagementOperations.getExchange(exchangeName);
		}
		
		String queueName = String.format("tasks.%s.in", StringUtils.trim(agent));
		if(notExistQueue(queueName)){
			Queue queue = declareQueue(queueName);				
			rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(exchange));
			log.info("Create queue: {} - exchange: {}", queueName, exchangeName);
		}
	}
}