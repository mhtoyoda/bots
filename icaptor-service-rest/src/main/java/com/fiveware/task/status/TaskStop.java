package com.fiveware.task.status;

import java.util.Date;

import com.fiveware.model.Task;

public class TaskStop implements TaskStatusStep {

	private Task task;

	public TaskStop() {
	}

	public TaskStop(Task task) {
		this.task = task;
	}

	@Override
	public void start() {
		throw new RuntimeException("Task Status not allow");
	}

	@Override
	public void stop() {
		throw new RuntimeException("Task Status not allow");
	}

	@Override
	public void scheduled() {
		throw new RuntimeException("Task Status not allow");
	}

	@Override
	public void pause() {
		throw new RuntimeException("Task Status not allow");
	}

	@Override
	public void consolidating() {
		throw new RuntimeException("Task Status not allow");
	}

	@Override
	public void error() {
		throw new RuntimeException("Task Status not allow");
	}

	@Override
	public void finish() {
		throw new RuntimeException("Task Status not allow");
	}

	@Override
	public void restart() {
		this.task.setStatus(TaskStatus.START.name());
		this.task.setLastUpdateTime(new Date());
		int countExecutions = this.task.getCountExecutions() + 1;
		this.task.setCountExecutions(countExecutions);
	}
}