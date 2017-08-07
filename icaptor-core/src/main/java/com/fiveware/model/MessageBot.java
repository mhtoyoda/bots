package com.fiveware.model;

import java.io.Serializable;
import java.util.List;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

@AutoProperty
public class MessageBot implements Serializable {

	private final Long taskId;
	private final Long itemTaskId;
	private final String line;
	private List<String> lineResult;
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

	public List<String> getLineResult() {
		return lineResult;
	}
	
	public void setLineResult(List<String> lineResult) {
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