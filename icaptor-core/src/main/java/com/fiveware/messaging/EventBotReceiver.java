package com.fiveware.messaging;

import com.fiveware.model.message.MessageBot;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service("eventBotReceiver")
public class EventBotReceiver implements Receiver<MessageBot>,Consumer {


	static Logger logger = LoggerFactory.getLogger(EventBotReceiver.class);


	@Autowired
	private RabbitTemplate rabbit;
	
	@Override
	public MessageBot receive(String queue){
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

	@Override
	public void handleConsumeOk(String s) {
		logger.debug(s);
	}

	@Override
	public void handleCancelOk(String s) {
		logger.debug(s);
	}

	@Override
	public void handleCancel(String s) throws IOException {
		logger.debug(s);
	}

	@Override
	public void handleShutdownSignal(String s, ShutdownSignalException e) {
		logger.debug(s);
	}

	@Override
	public void handleRecoverOk(String s) {
		logger.debug(s);
	}

	@Override
	public void handleDelivery(String s, Envelope envelope, AMQP.BasicProperties basicProperties, byte[] bytes) throws IOException {
		Map map = (HashMap) SerializationUtils.deserialize(bytes);
		System.out.println("Error Number "+ map.get("message number") + " received.");
	}
}