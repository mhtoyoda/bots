package com.fiveware.task.status;

import java.util.Date;

import com.fiveware.model.Task;

public class TaskConsolidating implements TaskStatusStep {

	private Task task;

	public TaskConsolidating() {}

	public TaskConsolidating(Task task) {
		this.task = task;
	}

	@Override
	public void finish() {
		this.task.setStatus(TaskStatus.FINISH.name());
		this.task.setEndTime(new Date());
		this.task.setLastUpdateTime(new Date());
	}

	@Override
	public void error() {
		this.task.setStatus(TaskStatus.ERROR.name());
		this.task.setLastUpdateTime(new Date());
	}

	@Override
	public void pause() {
		this.task.setStatus(TaskStatus.PAUSE.name());
		this.task.setLastUpdateTime(new Date());
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
	public void stop() {
		throw new RuntimeException("Task Status not allow");
	}

	@Override
	public void consolidating() {
		throw new RuntimeException("Task Status not allow");
	}

	@Override
	public void restart() {
		throw new RuntimeException("Task Status not allow");
	}
}