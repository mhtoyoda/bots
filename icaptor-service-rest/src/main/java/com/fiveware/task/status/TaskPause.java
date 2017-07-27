package com.fiveware.task.status;

import java.util.Date;

import com.fiveware.model.Task;

public class TaskPause {

	private Task task;

	public TaskPause(Task task) {
		this.task = task;
	}

	public void start(){		
		this.task.setStatus(TaskStatus.START.name());
		this.task.setLastUpdateTime(new Date());
		int countExecutions = this.task.getCountExecutions()+1;
		this.task.setCountExecutions(countExecutions);
	}

	public void stop(){
		this.task.setStatus(TaskStatus.STOP.name());
		this.task.setLastUpdateTime(new Date());
		this.task.setEndTime(new Date());
	}
}