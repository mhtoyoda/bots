package com.fiveware.workflow.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TaskWorkflow {

	private static final Logger log = LoggerFactory.getLogger(TaskWorkflow.class);
	
	@Autowired
	private SequenceTaskWorkflow sequenceTaskWorkflow;
	
	@Autowired
	private CreateWorkflowTask createWorkflowTask;
	
	@Autowired
	private ScheduledTaskWorkflow scheduledTaskWorkflow;
	
	@Scheduled(cron = "*/60 * * * * *")
	public void createWorkFlowTaskCron(){
		log.info("Execute createWorkFlowTaskCron");
		createWorkflowTask.createWorkflows();
	}

	@Scheduled(cron = "0 0/3 * * * ?")
	public void sequenceTask(){
		log.info("Execute sequenceTask");
		sequenceTaskWorkflow.sequenceTask();
	}
	
	@Scheduled(cron = "0 0/5 * * * ?")
	public void scheduledTask(){
		log.info("Execute scheduledTask");
		scheduledTaskWorkflow.scheduledTask();
	}
}


