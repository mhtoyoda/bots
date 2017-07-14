package com.fiveware.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fiveware.task.TaskMessageBot;


@Service("taskMessageProducer")
public class TaskMessageProducer implements Producer<TaskMessageBot> {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Override
	public void send(String queue, TaskMessageBot message){
		rabbitTemplate.convertAndSend(queue, message);
	}
}
