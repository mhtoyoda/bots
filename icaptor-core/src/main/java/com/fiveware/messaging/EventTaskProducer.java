package com.fiveware.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fiveware.model.message.MessageTask;

/**
 * Created by valdisnei on 13/06/17.
 */
@Service("eventTaskProducer")
public class EventTaskProducer implements Producer<MessageTask> {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void send(String queue, MessageTask message) {
    	rabbitTemplate.setExchange("topic-exchange");
    	rabbitTemplate.setRoutingKey("task.in");
    	rabbitTemplate.convertAndSend(queue, message);
    }
}
