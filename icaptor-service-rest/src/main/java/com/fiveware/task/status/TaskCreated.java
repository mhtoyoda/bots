package com.fiveware.task.status;

import java.util.Date;

import com.fiveware.model.Task;

public class TaskCreated {
	
	private Task task;
	
	public TaskCreated(Task task) {
		this.task = task;
	}

	public void start() {
		this.task.setStatus(TaskStatus.START.name());
		this.task.setCountExecutions(1);
		this.task.setStartTime(new Date());
		this.task.setLastUpdateTime(new Date());
	}

	public void scheduled() {
		this.task.setStatus(TaskStatus.SCHEDULED.name());		
	}
}