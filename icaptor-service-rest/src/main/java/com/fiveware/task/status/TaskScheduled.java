package com.fiveware.task.status;

import java.util.Date;

import com.fiveware.model.Task;

public class TaskScheduled {
	
	private Task task;
	
	public TaskScheduled(Task task) {
		this.task = task;
	}
	
	public void start() {
		this.task.setStatus(TaskStatus.START.name());
		this.task.setCountExecutions(1);
		this.task.setStartTime(new Date());
		this.task.setLastUpdateTime(new Date());
	}
}