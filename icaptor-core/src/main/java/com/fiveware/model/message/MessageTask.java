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
	private Long itemTask;
	
	public MessageTask(TypeMessage typeMessage, Long itemTask) {
		super();
		this.typeMessage = typeMessage;
		this.itemTask = itemTask;
	}

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
	
	public Long getItemTask() {
		return itemTask;
	}
	
	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}
}