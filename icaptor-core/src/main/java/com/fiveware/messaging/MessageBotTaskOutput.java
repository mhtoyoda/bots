package com.fiveware.messaging;

import java.util.List;
import java.util.Map;

import com.fiveware.model.BotParameterKeyValue;
import com.fiveware.model.MessageBotTask;

public class MessageBotTaskOutput extends MessageBotTask {

	private final String record;
	private final String result;
	private String messageError;

	public MessageBotTaskOutput(Long taskId, Long timeStamp, TypeMessage typeMessage,
			List<Map<String, BotParameterKeyValue>> parametersUsers,
			List<Map<String, BotParameterKeyValue>> parametersBot, String record, String result) {
		super(taskId, timeStamp, typeMessage, parametersUsers, parametersBot);
		this.record = record;
		this.result = result;
	}

	public String getRecord() {
		return record;
	}

	public String getResult() {
		return result;
	}
	
	public String getMessageError() {
		return messageError;
	}
	
	public void setMessageError(String messageError) {
		this.messageError = messageError;
	}

}
