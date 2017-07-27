package com.fiveware.task.status;

import java.util.Date;

import com.fiveware.model.Task;

public class TaskError {
	
	private Task task;
	
	public TaskError(Task task) {
		this.task = task;
	}

	public void restart() {
		this.task.setStatus(TaskStatus.START.name());
		this.task.setCountExecutions(1);
		this.task.setLastUpdateTime(new Date());
	}

	public void scheduled() {
		this.task.setStatus(TaskStatus.SCHEDULED.name());
		this.task.setLastUpdateTime(new Date());
	}
	
	public void finish() {
		this.task.setStatus(TaskStatus.STOP.name());
		this.task.setLastUpdateTime(new Date());
	}
}