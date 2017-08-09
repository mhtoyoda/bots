package com.fiveware.model;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import java.io.Serializable;

@AutoProperty
public class MessageBot implements Serializable {

	private final Long taskId;
	private final Long itemTaskId;
	private final String line;
	private String lineResult;
	private final MessageHeader messageHeader;

	public MessageBot(Long taskId, Long itemTaskId, String line, MessageHeader messageHeader) {
		this.taskId = taskId;
		this.itemTaskId = itemTaskId;
		this.line = line;
		this.messageHeader = messageHeader;
	}
	
	public Long getTaskId() {
		return taskId;
	}

	public String getLine() {
		return line;
	}

	public String getLineResult() {
		return lineResult;
	}
	public void setLineResult(String lineResult) {
		this.lineResult = lineResult;
	}

	public MessageHeader getMessageHeader() {
		return messageHeader;
	}

	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}
}