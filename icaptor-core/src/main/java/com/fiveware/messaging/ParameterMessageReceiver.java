package com.fiveware.messaging;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Objects;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fiveware.model.message.MessageParameterAgent;

@Service("parameterMessageReceiver")
public class ParameterMessageReceiver implements Receiver<MessageParameterAgent> {
	
	@Autowired
	private RabbitTemplate rabbit;
	
	@Override
	public MessageParameterAgent receive(String queueName){	
		Message message = rabbit.receive(queueName);
		return Objects.isNull(message) ? null : convert(message.getBody());		
	}
	
	private MessageParameterAgent convert(byte[] body){
		 ByteArrayInputStream in = new ByteArrayInputStream(body);
		    try {
				ObjectInputStream is = new ObjectInputStream(in);
				MessageParameterAgent readObject = (MessageParameterAgent) is.readObject();
				return readObject;
			} catch (IOException | ClassNotFoundException e) {
				return null;
			}
	}
}