package com.fiveware.model;

import java.util.List;
import java.util.Map;

import com.fiveware.messaging.TypeMessage;

public class MessageBotTask {

	private final Long taskId;
	private final Long timeStamp;
	private final TypeMessage typeMessage;
	private final List<Map<String, BotParameterKeyValue>> parametersUsers;
	private final List<Map<String, BotParameterKeyValue>> parametersBot;

	public MessageBotTask(Long taskId, Long timeStamp, TypeMessage typeMessage,
			List<Map<String, BotParameterKeyValue>> parametersUsers,
			List<Map<String, BotParameterKeyValue>> parametersBot) {
		super();
		this.taskId = taskId;
		this.timeStamp = timeStamp;
		this.typeMessage = typeMessage;
		this.parametersUsers = parametersUsers;
		this.parametersBot = parametersBot;
	}

	public Long getTaskId() {
		return taskId;
	}

	public Long getTimeStamp() {
		return timeStamp;
	}

	public TypeMessage getTypeMessage() {
		return typeMessage;
	}

	public List<Map<String, BotParameterKeyValue>> getParametersUsers() {
		return parametersUsers;
	}

	public List<Map<String, BotParameterKeyValue>> getParametersBot() {
		return parametersBot;
	}

}
