package com.fiveware.messaging;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Objects;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fiveware.model.MessageBot;

@Service("eventInputDictionaryReceiver")
public class EventInputDictionaryReceiver implements Receiver<MessageBot> {
	
	@Autowired
	private RabbitTemplate rabbit;
	
	@Override
	public MessageInputDictionary receive(String queue){
		Message message = rabbit.receive(queue);
		return  Objects.isNull(message) ? null : convert(message.getBody());
	}
	
	private MessageBot convert(byte[] body) {
		ByteArrayInputStream in = new ByteArrayInputStream(body);
		try {
			ObjectInputStream is = new ObjectInputStream(in);
			MessageBot readObject = (MessageBot) is.readObject();
			return readObject;
		} catch (IOException | ClassNotFoundException e) {
			return null;
		}
	}
}