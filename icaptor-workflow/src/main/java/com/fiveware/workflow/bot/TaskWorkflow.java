package com.fiveware.workflow.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TaskWorkflow {

	@Autowired
	private SequenceTaskWorkflow sequenceTaskWorkflow;
	
	@Autowired
	private CreateWorkflowTask createWorkflowTask; 
	
	@Scheduled(cron = "0 30 13 * * *")
	public void createWorkFlowTaskCron(){
		createWorkflowTask.createWorkflows();
	}

	@Scheduled(fixedDelay = 180000)
	public void sequenceTask(){
		sequenceTaskWorkflow.sequenceTask();
	}
}
