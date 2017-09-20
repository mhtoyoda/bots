package com.fiveware.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskResolver {
	
	@Autowired
	private SuspendedTask suspendedTask;
	
	@Autowired
	private ProcessingTask processingTask;
	
	@Autowired
	private TimeoutItemTask timeoutItemTask;

	@Autowired
	private TaskProcessedTask taskProcessedTask;
	
	@Autowired
	private CanceledTask canceledTask;
	
	public void process() {
		checkTaskProcessing();
		checkTaskSuspending();
		checkTimeout();		
		checkTaskProcessed();
		checkTaskCanceled();
	}
	
	private void checkTaskProcessed() {
		Runnable taskProcessedThread = () -> {taskProcessedTask.checkTaskProcessed();};
		new Thread(taskProcessedThread).start();
	}

	private void checkTimeout() {
		Runnable timeoutItemTaskThread = () -> {timeoutItemTask.checkTimeout();};
		new Thread(timeoutItemTaskThread).start();		
	}

	private void checkTaskSuspending(){
		Runnable suspendingThread = () -> {suspendedTask.applyUpdateTaskSuspending();};
		new Thread(suspendingThread).start();
	}
	
	private void checkTaskProcessing(){
		Runnable processingThread = () -> {processingTask.applyUpdateTaskProcessing();};
		new Thread(processingThread).start();
	}
	
	private void checkTaskCanceled(){
		Runnable processingThread = () -> {canceledTask.applyUpdateTaskCanceled();};
		new Thread(processingThread).start();
	}
}
