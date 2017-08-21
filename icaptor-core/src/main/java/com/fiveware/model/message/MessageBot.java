package com.fiveware.model.message;

import com.fiveware.task.StatusProcessItemTaskEnum;
import com.fiveware.task.StatusProcessTaskEnum;
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
	private StatusProcessTaskEnum statuProcessEnum;
	private StatusProcessItemTaskEnum statusProcessItemTaskEnum;
	private Exception exception;
	private int attemptsCount;

	public MessageBot(Long taskId, Long itemTaskId, String line, MessageHeader messageHeader) {
		this.taskId = taskId;
		this.itemTaskId = itemTaskId;
		this.line = line;
		this.messageHeader = messageHeader;
	}
	
	public Long getTaskId() {
		return taskId;
	}

	public Long getItemTaskId() {return itemTaskId;}

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

	public StatusProcessTaskEnum getStatuProcessEnum() {return statuProcessEnum;}
	public void setStatuProcessEnum(StatusProcessTaskEnum statuProcessEnum) {this.statuProcessEnum = statuProcessEnum;}

	public StatusProcessItemTaskEnum getStatusProcessItemTaskEnum() {return statusProcessItemTaskEnum;}

	public void setStatusProcessItemTaskEnum(StatusProcessItemTaskEnum statusProcessItemTaskEnum) {
		this.statusProcessItemTaskEnum = statusProcessItemTaskEnum;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public int getAttemptsCount() {
		return attemptsCount;
	}

	public void setAttemptsCount(int attemptsCount) {
		this.attemptsCount = attemptsCount;
	}

	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}
}