package com.fiveware.model;

import java.io.Serializable;
import java.util.List;

import com.fiveware.messaging.TypeMessage;

public class MessageAgent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3098911770867488482L;

	private String host;
	private String agent;
	private String ip;
	private TypeMessage typeMessage;
	private String description;
	private List<MessageAgentBot> messageAgentBots;
	
	public MessageAgent(){}
	
	public MessageAgent(String host, String agent, String ip, TypeMessage typeMessage, String description) {
		super();
		this.host = host;
		this.agent = agent;
		this.agent = agent;
		this.ip = ip;
		this.typeMessage = typeMessage;
		this.description = description;
	}
	
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}
	
	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public TypeMessage getTypeMessage() {
		return typeMessage;
	}

	public void setTypeMessage(TypeMessage typeMessage) {
		this.typeMessage = typeMessage;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public List<MessageAgentBot> getMessageAgentBots() {
		return messageAgentBots;
	}

	public void setMessageAgentBots(List<MessageAgentBot> messageAgentBots) {
		this.messageAgentBots = messageAgentBots;
	}

	@Override
	public String toString() {
		return "Message [host=" + host + ", typeMessage=" + typeMessage.name() + ", description=" + description + "]";
	}
}