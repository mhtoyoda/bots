package com.fiveware.model;

import java.io.Serializable;

import com.fiveware.messaging.TypeMessage;

public class MessageAgent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3098911770867488482L;

	private String host;
	private TypeMessage typeMessage;
	private String description;
	
	public MessageAgent(){}
	
	public MessageAgent(String host, TypeMessage typeMessage, String description) {
		super();
		this.host = host;
		this.typeMessage = typeMessage;
		this.description = description;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public TypeMessage getTypeMessage() {
		return typeMessage;
	}

	public void setTypeMessage(TypeMessage typeMessage) {
		this.typeMessage = typeMessage;
	}

	@Override
	public String toString() {
		return "Message [host=" + host + ", typeMessage=" + typeMessage.name() + ", description=" + description + "]";
	}
}