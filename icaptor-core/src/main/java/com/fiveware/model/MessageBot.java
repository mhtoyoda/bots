package com.fiveware.model;

import java.io.Serializable;
import java.util.List;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import com.fiveware.messaging.TypeMessage;
import com.google.common.collect.Lists;

@AutoProperty
public class MessageBot implements Serializable {

	private final List<String> line;
	private final List<String> lineResult;
	private final TypeMessage typeMessage;
	private final MessageHeader messageHeader;
	private final String pathFile;
	private Integer qtdeInstances;
	
	public MessageBot(TypeMessage typeMessage, MessageHeader messageHeader) {
		this.line = Lists.newArrayList();
		this.lineResult = Lists.newArrayList();
		this.typeMessage = typeMessage;
		this.messageHeader = messageHeader;
		this.pathFile = "";
	}

	public MessageBot(List<String> line, List<String> lineResult, TypeMessage typeMessage,
			MessageHeader messageHeader, String pathFile) {
		this.line = line;
		this.lineResult = lineResult;
		this.typeMessage = typeMessage;
		this.messageHeader = messageHeader;
		this.pathFile = pathFile;
	}

	public TypeMessage getTypeMessage() {
		return typeMessage;
	}

	public List<String> getLine() {
		return line;
	}

	public List<String> getLineResult() {
		return lineResult;
	}

	public MessageHeader getMessageHeader() {
		return messageHeader;
	}

	public String getPathFile() {
		return pathFile;
	}
	
	public Integer getQtdeInstances() {
		return qtdeInstances;
	}

	public void setQtdeInstances(Integer qtdeInstances) {
		this.qtdeInstances = qtdeInstances;
	}

	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}
}