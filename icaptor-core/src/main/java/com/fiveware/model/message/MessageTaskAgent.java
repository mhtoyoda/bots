package com.fiveware.model.message;

import java.io.Serializable;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

@AutoProperty
public class MessageTaskAgent extends MessageAgent implements Serializable {

	private Long taskId;
	private Long itemTaskId;

	public Long getTaskId() {
		return taskId;
	}

	public Long getItemTaskId() {
		return itemTaskId;
	}

	public MessageTaskAgent(Long taskId, Long itemTaskId) {
		super();
		this.taskId = taskId;
		this.itemTaskId = itemTaskId;
	}

	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}
}