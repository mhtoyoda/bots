package com.fiveware.task;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fiveware.model.BotParameterKeyValue;
import com.fiveware.task.status.TaskStatus;

public class TaskMessageBot implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -729384364256323660L;

	private Long taskId;
	private Integer qtdeInstances;
	private TaskStatus taskStatus;
	private Boolean loginShared;
	private List<Map<String, BotParameterKeyValue>> parameters;
	
	public TaskMessageBot(Long taskId, Integer qtdeInstances, TaskStatus taskStatus, Boolean loginShared,
			List<Map<String, BotParameterKeyValue>> parameters) {
		super();
		this.taskId = taskId;
		this.qtdeInstances = qtdeInstances;
		this.taskStatus = taskStatus;
		this.loginShared = loginShared;
		this.parameters = parameters;
	}

	public Long getTaskId() {
		return taskId;
	}

	public Integer getQtdeInstances() {
		return qtdeInstances;
	}

	public TaskStatus getTaskStatus() {
		return taskStatus;
	}

	public Boolean getLoginShared() {
		return loginShared;
	}

	public List<Map<String, BotParameterKeyValue>> getParameters() {
		return parameters;
	}
}
