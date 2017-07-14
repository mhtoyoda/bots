package com.fiveware.messaging;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Objects;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fiveware.task.TaskMessageBot;

@Service("taskMessageReceiver")
public class TaskMessageReceiver implements Receiver<TaskMessageBot> {
	
	@Autowired
	private RabbitTemplate rabbit;
	
	@Override
	public TaskMessageBot receive(String queue){
		Message message = rabbit.receive(queue);
		return  Objects.isNull(message) ? null : convert(message.getBody());
	}
	
	private TaskMessageBot convert(byte[] body) {
		ByteArrayInputStream in = new ByteArrayInputStream(body);
		try {
			ObjectInputStream is = new ObjectInputStream(in);
			TaskMessageBot readObject = (TaskMessageBot) is.readObject();
			return readObject;
		} catch (IOException | ClassNotFoundException e) {
			return null;
		}
	}
}