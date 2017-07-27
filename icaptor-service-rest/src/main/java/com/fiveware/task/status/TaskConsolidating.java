package com.fiveware.task.status;

import java.util.Date;

import com.fiveware.model.Task;

public class TaskConsolidating {
	
	private Task task;
	
	public TaskConsolidating(Task task) {
		this.task = task;
	}
	
	public void finish() {
		this.task.setStatus(TaskStatus.FINISH.name());
		this.task.setEndTime(new Date());
		this.task.setLastUpdateTime(new Date());
	}

	public void error() {
		this.task.setStatus(TaskStatus.ERROR.name());
		this.task.setLastUpdateTime(new Date());
	}
	
	public void pause(){
		this.task.setStatus(TaskStatus.PAUSE.name());
		this.task.setLastUpdateTime(new Date());		
	}
}