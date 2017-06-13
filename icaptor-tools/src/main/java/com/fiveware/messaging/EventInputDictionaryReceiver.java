package com.fiveware.messaging;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Objects;

import com.fiveware.model.MessageAgent;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fiveware.model.MessageInputDictionary;

@Service("eventInputDictionaryReceiver")
public class EventInputDictionaryReceiver implements Receiver<MessageInputDictionary> {
	
	@Autowired
	private RabbitTemplate rabbit;
	
	@Override
	public MessageInputDictionary receive(){
		Message message = rabbit.receive("BOT");
		return  Objects.isNull(message) ? null : convert(message.getBody());
	}
	
	private MessageInputDictionary convert(byte[] body) {
		ByteArrayInputStream in = new ByteArrayInputStream(body);
		try {
			ObjectInputStream is = new ObjectInputStream(in);
			MessageInputDictionary readObject = (MessageInputDictionary) is.readObject();
			return readObject;
		} catch (IOException | ClassNotFoundException e) {
			return null;
		}
	}
}