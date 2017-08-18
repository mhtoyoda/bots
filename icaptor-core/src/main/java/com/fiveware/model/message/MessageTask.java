package com.fiveware.model.message;

import java.io.Serializable;
import java.util.List;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import com.fiveware.messaging.TypeMessage;
import com.google.common.collect.Lists;

@AutoProperty
public class MessageTask implements Serializable{

	private String nameQueueTask;
	private String botName;
	private TypeMessage typeMessage;
	private List<String> agents;

	public MessageTask(String nameQueueTask, String botName) {
		super();
		this.nameQueueTask = nameQueueTask;
		this.botName = botName;
		this.agents = Lists.newArrayList();
	}

	public String getNameQueueTask() {
		return nameQueueTask;
	}
	
	public String getBotName() {
		return botName;
	}

	public TypeMessage getTypeMessage() {
		return typeMessage;
	}
	
	public List<String> getAgents() {
		return agents;
	}
	
	public void addAgent(String agent){
		agents.add(agent);
	}
	
	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}
}