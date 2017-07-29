package com.fiveware.messaging;

import java.util.List;
import java.util.Map;

import com.fiveware.model.BotParameterKeyValue;
import com.fiveware.model.MessageBotTask;

public class MessageBotTaskInput extends MessageBotTask {

	private final String record;
	private final Integer totalLines;
	private String pathFile;

	public MessageBotTaskInput(Long taskId, Long timeStamp, TypeMessage typeMessage,
			List<Map<String, BotParameterKeyValue>> parametersUsers,
			List<Map<String, BotParameterKeyValue>> parametersBot, String record, Integer totalLines) {
		super(taskId, timeStamp, typeMessage, parametersUsers, parametersBot);
		this.record = record;
		this.totalLines = totalLines;
	}

	public String getRecord() {
		return record;
	}

	public Integer getTotalLines() {
		return totalLines;
	}

	public String getPathFile() {
		return pathFile;
	}

}
