package com.fiveware.model.message;

import java.io.Serializable;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

@AutoProperty
public class MessageParameterAgent extends MessageAgent implements Serializable {

	private String nameBot;
	private Long taskId;
	private Long itemTaskId;
	private Boolean credential;
	private String fieldValue;
	
	public String getNameBot() {
		return nameBot;
	}

	public Long getTaskId() {
		return taskId;
	}

	public Long getItemTaskId() {
		return itemTaskId;
	}
	
	public Boolean getCredential() {
		return credential;
	}

	public String getFieldValue() {
		return fieldValue;
	}
	
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}
	
	public MessageParameterAgent(String nameAgent, String nameBot, Long taskId, Long itemTaskId, Boolean credential) {
		super();
		setAgent(nameAgent);
		this.nameBot = nameBot;
		this.taskId = taskId;
		this.itemTaskId = itemTaskId;
		this.credential = credential;
	}

	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}
}