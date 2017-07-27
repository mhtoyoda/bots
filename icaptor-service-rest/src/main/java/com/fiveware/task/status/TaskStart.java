package com.fiveware.task.status;

import java.util.Date;

import com.fiveware.model.Task;

public class TaskStart implements TaskStatusStep {

	private Task task;

	public TaskStart() {}

	public TaskStart(Task task) {
		this.task = task;
	}

	@Override
	public void pause() {
		this.task.setStatus(TaskStatus.PAUSE.name());
		this.task.setLastUpdateTime(new Date());
	}

	@Override
	public void stop() {
		this.task.setStatus(TaskStatus.STOP.name());
		this.task.setLastUpdateTime(new Date());
		this.task.setEndTime(new Date());
	}

	@Override
	public void consolidating() {
		this.task.setStatus(TaskStatus.CONSOLIDATING.name());
		this.task.setLastUpdateTime(new Date());
	}

	@Override
	public void error() {
		this.task.setStatus(TaskStatus.ERROR.name());
		int countExecutions = this.task.getCountExecutions() + 1;
		this.task.setCountExecutions(countExecutions);
	}

	@Override
	public void start() {
		throw new RuntimeException("Task Status not allow");

	}

	@Override
	public void scheduled() {
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