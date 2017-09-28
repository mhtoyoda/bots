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
	
	@Scheduled(cron = "0 23 16 * * *")
//	@Scheduled(cron = "0 0/30 * * * ?")
	public void createWorkFlowTaskCron(){
		createWorkflowTask.createWorkflows();
	}

	@Scheduled(cron = "0 0/5 * * * ?")
	public void sequenceTask(){
		sequenceTaskWorkflow.sequenceTask();
	}
}
