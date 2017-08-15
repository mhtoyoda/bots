package com.fiveware.messaging;

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

	public void purgeQueue(String queueName){
		log.info("Purge queue: {}", queueName);
		 rabbitAdmin.purgeQueue(queueName,true);
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
		}else{
			exchange = (FanoutExchange) amqpManagementOperations.getExchange(exchangeName);
		}
		rabbitAdmin.declareExchange(exchange);
		
		agents.stream().forEach(agent -> {
			String queueName = "tasks."+agent+".in";
			if(notExistQueue(queueName)){
				Queue queue = declareQueue(queueName);				
				rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(exchange));
			}
		});
	}
}