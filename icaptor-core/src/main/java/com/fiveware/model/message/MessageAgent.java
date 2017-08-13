package com.fiveware.model.message;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.fiveware.messaging.TypeMessage;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

@AutoProperty
public class MessageAgent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3098911770867488482L;

	private String host;
	private String agent;
	private String ip;
	private int port;
	private TypeMessage typeMessage;
	private String description;
	private List<MessageAgentBot> messageAgentBots;
	private Set<String> nameQueues;

	public MessageAgent(){}
	
	public MessageAgent(String host, String agent, String ip, int port, TypeMessage typeMessage, String description) {
		super();
		this.host = host;
		this.agent = agent;
		this.agent = agent;
		this.ip = ip;
		this.port = port;
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
	
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
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

	public void setNameQueues(Set<String> nameQueues) {this.nameQueues = nameQueues;}

	public Set<String> getNameQueues() {return nameQueues;}

	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}
}