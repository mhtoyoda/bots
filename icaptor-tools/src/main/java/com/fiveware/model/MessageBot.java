package com.fiveware.model;

import java.io.Serializable;
import java.util.List;

import com.fiveware.messaging.TypeMessage;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

@AutoProperty
public class MessageBot implements Serializable {

	private final List<String> line;
	private final TypeMessage typeMessage;
	private final String description;
	private final MessageHeader messageHeader;
	private List<String> lineResult;
	
	public MessageBot(List<String> line, TypeMessage typeMessage, String description, MessageHeader messageHeader) {
		this.line = line;
		this.typeMessage = typeMessage;
		this.description = description;
		this.messageHeader = messageHeader;
	}

	public TypeMessage getTypeMessage() {return typeMessage;}

	public String getDescription() {return description;}

	public List<String> getLine() {return line;}
	
	public List<String> getLineResult() {return lineResult;}

	public void setLineResult(List<String> lineResult) {this.lineResult = lineResult;}

	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}
}