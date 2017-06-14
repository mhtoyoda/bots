package com.fiveware.messaging;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Objects;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fiveware.model.MessageAgent;

@Service("eventMessageReceiver")
public class EventMessageReceiver implements Receiver<MessageAgent> {
	
	@Autowired
	private RabbitTemplate rabbit;
	
	@Override
	public MessageAgent receive(String queueName){	
		Message message = rabbit.receive(queueName);
		return Objects.isNull(message) ? null : convert(message.getBody());		
	}
	
	private MessageAgent convert(byte[] body){
		 ByteArrayInputStream in = new ByteArrayInputStream(body);
		    try {
				ObjectInputStream is = new ObjectInputStream(in);
				MessageAgent readObject = (MessageAgent) is.readObject();
				return readObject;
			} catch (IOException | ClassNotFoundException e) {
				return null;
			}
	}
}