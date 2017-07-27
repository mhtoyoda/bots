package com.fiveware.task.status;

import java.util.Date;

import com.fiveware.model.Task;

public class TaskStart {

	private Task task;

	public TaskStart(Task task) {
		this.task = task;
	}

	public void pause(){		
		this.task.setStatus(TaskStatus.PAUSE.name());
		this.task.setLastUpdateTime(new Date());
	}

	public void stop(){
		this.task.setStatus(TaskStatus.STOP.name());
		this.task.setLastUpdateTime(new Date());
		this.task.setEndTime(new Date());
	}

	public void consolidating(){
		this.task.setStatus(TaskStatus.CONSOLIDATING.name());
		this.task.setLastUpdateTime(new Date());
	}

	public void error(){
		this.task.setStatus(TaskStatus.ERROR.name());
		int countExecutions = this.task.getCountExecutions()+1;
		this.task.setCountExecutions(countExecutions);
	}
}