package com.fiveware.model;

import java.io.Serializable;
import java.util.StringJoiner;

import com.fiveware.messaging.TypeMessage;
import com.google.common.base.Joiner;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

@AutoProperty
public class MessageInputDictionary implements Serializable {

	private String line;
	private TypeMessage typeMessage;
	private String description;


	public MessageInputDictionary() {
	}

	public String getLine() {
		return line;
	}

	public String getDescription() {
		return description;
	}

	public TypeMessage getTypeMessage() {
		return typeMessage;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public void setTypeMessage(TypeMessage typeMessage) {
		this.typeMessage = typeMessage;
	}


	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}
}