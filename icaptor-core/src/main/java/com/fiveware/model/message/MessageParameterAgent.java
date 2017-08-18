package com.fiveware.model.message;

import java.io.Serializable;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

@AutoProperty
public class MessageParameterAgent extends MessageAgent implements Serializable {

	private String nameBot;
	private Long taskId;
	private Long itemTaskId;

	public String getNameBot() {
		return nameBot;
	}

	public Long getTaskId() {
		return taskId;
	}

	public Long getItemTaskId() {
		return itemTaskId;
	}
	
	public MessageParameterAgent(String nameAgent, String nameBot, Long taskId, Long itemTaskId) {
		super();
		setAgent(nameAgent);
		this.nameBot = nameBot;
		this.taskId = taskId;
		this.itemTaskId = itemTaskId;
	}

	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}
}