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
	private final String description;
	private final MessageHeader messageHeader;
	private final String pathFile;

	public MessageBot(TypeMessage typeMessage, String description, MessageHeader messageHeader) {
		this.line = Lists.newArrayList();
		this.lineResult = Lists.newArrayList();
		this.typeMessage = typeMessage;
		this.description = description;
		this.messageHeader = messageHeader;
		this.pathFile = "";
	}

	public MessageBot(List<String> line, List<String> lineResult, TypeMessage typeMessage, String description,
			MessageHeader messageHeader, String pathFile) {
		this.line = line;
		this.lineResult = lineResult;
		this.typeMessage = typeMessage;
		this.description = description;
		this.messageHeader = messageHeader;
		this.pathFile = pathFile;
	}

	public TypeMessage getTypeMessage() {
		return typeMessage;
	}

	public String getDescription() {
		return description;
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

	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}
}