package com.fiveware.task.status;

public interface TaskStatusStep {

	public void start();
	public void scheduled();
	public void pause();
	public void stop();
	public void consolidating();
	public void error();
	public void finish();
	public void restart();
}
