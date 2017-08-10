package com.fiveware.model.message;

import java.io.Serializable;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

@AutoProperty
public class MessageTask implements Serializable{

	private String nameQueueTask;
	private String botName;

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
	
	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}
}