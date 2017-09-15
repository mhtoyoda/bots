package com.fiveware.workflow.bot;

import com.fiveware.workflow.model.WorkflowBot;
import com.fiveware.workflow.model.WorkflowBotStep;

public interface WorkflowSequence {

	Long createTask(WorkflowBotStep workflowBotStep);

	Long createTaskByWorkflow(WorkflowBot workflowBot);
	
}