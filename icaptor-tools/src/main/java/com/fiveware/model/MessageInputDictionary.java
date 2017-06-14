package com.fiveware.model;

import java.io.Serializable;
import java.util.StringJoiner;

import com.fiveware.messaging.TypeMessage;
import com.google.common.base.Joiner;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

@AutoProperty
public class MessageInputDictionary implements Serializable {

	private final String line;
	private final TypeMessage typeMessage;
	private final String description;


	public MessageInputDictionary(String line, TypeMessage typeMessage, String description) {
		this.line = line;
		this.typeMessage = typeMessage;
		this.description = description;
	}

	public TypeMessage getTypeMessage() {return typeMessage;}

	public String getDescription() {return description;}

	public String getLine() {return line;}

	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}
}