package com.fiveware.task.status;

import java.util.Date;

import com.fiveware.model.Task;

public class TaskError implements TaskStatusStep {

	private Task task;

	public TaskError() {}

	public TaskError(Task task) {
		this.task = task;
	}

	@Override
	public void restart() {
		this.task.setStatus(TaskStatus.START.name());
		this.task.setCountExecutions(1);
		this.task.setLastUpdateTime(new Date());
	}

	@Override
	public void scheduled() {
		this.task.setStatus(TaskStatus.SCHEDULED.name());
		this.task.setLastUpdateTime(new Date());
	}

	@Override
	public void finish() {
		this.task.setStatus(TaskStatus.STOP.name());
		this.task.setLastUpdateTime(new Date());
	}

	@Override
	public void start() {
		throw new RuntimeException("Task Status not allow");
	}

	@Override
	public void pause() {
		throw new RuntimeException("Task Status not allow");
	}

	@Override
	public void stop() {
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
}