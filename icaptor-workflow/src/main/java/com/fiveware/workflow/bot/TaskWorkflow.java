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
	
//	@Scheduled(cron = "0 0 7 15,21,28 * ?")
	@Scheduled(fixedDelay = 60000)
	public void createWorkFlowTaskCron(){
		createWorkflowTask.createWorkflowsCron();
	}

	@Scheduled(fixedDelay = 180000)
	public void sequenceTask(){
		sequenceTaskWorkflow.sequenceTask();
	}
}
