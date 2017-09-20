package com.fiveware.workflow.bot;

import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fiveware.model.StatusProcessTaskEnum;
import com.fiveware.model.Task;
import com.fiveware.task.TaskManager;
import com.fiveware.workflow.model.StatusWorkflow;
import com.fiveware.workflow.model.WorkflowBot;
import com.fiveware.workflow.repository.WorkFlowBotRepository;

@Component
public class SequenceTaskWorkflow {

	@Autowired
	private TaskManager taskManager;
	
	@Autowired
	private WorkFlowBotRepository workFlowBotRepository;
	
	@Autowired
	private ProcessWorkflowTask processWorkflowTask;
	
	public void sequenceTask(){
		WorkflowBot workflowBot = workFlowBotRepository.findByStatus(StatusWorkflow.WAITING);
		Task task = taskManager.getTask(workflowBot.getId());
		if( null != task && task.getStatusProcess().getName().equals(StatusProcessTaskEnum.PROCESSED.getName())){
			if(null != workflowBot.getWorkflowBotStep() && StringUtils.isNotBlank(workflowBot.getWorkflowBotStep().getBotTarget())){
				Long taskId = processWorkflowTask.createTaskByWorkflow(workflowBot);
				if( null != taskId ){
					WorkflowBot workflowBotStepByStatus = workFlowBotRepository.findWorkflowBotStepByStatus(workflowBot.getWorkflowBotStep().getBotTarget(), StatusWorkflow.NEW);
					if(null != workflowBotStepByStatus){
						updateWorkFlowBotStatus(workflowBot, StatusWorkflow.COMPLETE);
						updateWorkFlowBotStatus(workflowBotStepByStatus, StatusWorkflow.WAITING);
					}					
				}
			}
		}
	}

	private WorkflowBot updateWorkFlowBotStatus(WorkflowBot workflowBot, StatusWorkflow statusWorkflow) {
		if(statusWorkflow == StatusWorkflow.COMPLETE){
			workflowBot.setDateUpdated(LocalDateTime.now());
		}
		workflowBot.setStatus(statusWorkflow);
		workflowBot = workFlowBotRepository.save(workflowBot);
		return workflowBot;
	}
}
