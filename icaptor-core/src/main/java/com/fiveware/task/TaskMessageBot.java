package com.fiveware.task;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fiveware.messaging.TypeMessage;
import com.fiveware.model.BotParameterKeyValue;

public class TaskMessageBot implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -729384364256323660L;

	private Long taskId;
	private Integer qtdeInstances;
	private TypeMessage typeMessage;
	private List<Map<String, BotParameterKeyValue>> parameters;
	
	public TaskMessageBot(Long taskId, Integer qtdeInstances,
			List<Map<String, BotParameterKeyValue>> parameters, TypeMessage typeMessage) {
		super();
		this.taskId = taskId;
		this.qtdeInstances = qtdeInstances;
		this.parameters = parameters;
		this.typeMessage = typeMessage;
	}

	public Long getTaskId() {
		return taskId;
	}

	public Integer getQtdeInstances() {
		return qtdeInstances;
	}
	
	public List<Map<String, BotParameterKeyValue>> getParameters() {
		return parameters;
	}
	
	public TypeMessage getTypeMessage() {
		return typeMessage;
	}
}
