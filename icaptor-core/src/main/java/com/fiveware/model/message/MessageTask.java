package com.fiveware.model.message;

import com.fiveware.messaging.TypeMessage;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import java.io.Serializable;

@AutoProperty
public class MessageTask implements Serializable{

	private String nameQueueTask;
	private String botName;
	private TypeMessage typeMessage;


	public MessageTask(String nameQueueTask, String botName) {
		super();
		this.nameQueueTask = nameQueueTask;
		this.botName = botName;
	}

	public String getNameQueueTask() {
		return nameQueueTask;
	}
	
	public String getBotName() {
		return botName;
	}

	public TypeMessage getTypeMessage() {
		return typeMessage;
	}



	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}
}