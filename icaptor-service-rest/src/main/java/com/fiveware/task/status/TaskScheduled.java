package com.fiveware.task.status;

import java.util.Date;

import com.fiveware.model.Task;

public class TaskScheduled implements TaskStatusStep {

	private Task task;

	public TaskScheduled() {}

	public TaskScheduled(Task task) {
		this.task = task;
	}

	@Override
	public void start() {
		this.task.setStatus(TaskStatus.START.name());
		this.task.setCountExecutions(1);
		this.task.setStartTime(new Date());
		this.task.setLastUpdateTime(new Date());
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

	@Override
	public void finish() {
		throw new RuntimeException("Task Status not allow");
	}

	@Override
	public void restart() {
		throw new RuntimeException("Task Status not allow");
	}
}