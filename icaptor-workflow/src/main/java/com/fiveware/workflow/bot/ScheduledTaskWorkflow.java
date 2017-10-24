package com.fiveware.workflow.bot;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fiveware.model.ItemTask;
import com.fiveware.model.StatusProcessItemTaskEnum;
import com.fiveware.model.StatusProcessTaskEnum;
import com.fiveware.model.Task;
import com.fiveware.task.TaskManager;
import com.fiveware.workflow.model.StatusWorkflow;
import com.fiveware.workflow.model.WorkflowBot;
import com.fiveware.workflow.repository.WorkFlowBotRepository;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;

@Component
public class ScheduledTaskWorkflow {

	private final Logger logger = LoggerFactory.getLogger(ScheduledTaskWorkflow.class);
	
	@Autowired
	private TaskManager taskManager;

	@Autowired
	private WorkFlowBotRepository workFlowBotRepository;

	@Autowired
	private ProcessWorkflowTask processWorkflowTask;

	public void scheduledTask() {
		Optional<WorkflowBot> optional = Optional.fromNullable(workFlowBotRepository.findByStatus(StatusWorkflow.SCHEDULED));
		if (optional.isPresent()) {			
			WorkflowBot workflowBot = optional.get();
			logger.info("Consultando workflow {}", workflowBot.getWorkflowBotStep().getBotSource());
			Task task = taskManager.getTask(workflowBot.getTaskId());
			if (canContinueTaskWorkflow(task, workflowBot)) {
				if (null != workflowBot.getWorkflowBotStep() && StringUtils.isNotBlank(workflowBot.getWorkflowBotStep().getBotTarget())) {
					Long taskId = processWorkflowTask.createTaskByWorkflow(workflowBot);
					if (null != taskId) {
						WorkflowBot workflowBotStepByStatus = workFlowBotRepository.findWorkflowBotStepByStatus(workflowBot.getWorkflowBotStep().getBotTarget(), StatusWorkflow.NEW);
						if (null != workflowBotStepByStatus) {
							updateWorkFlowBotStatus(workflowBot, StatusWorkflow.COMPLETE);
							workflowBotStepByStatus.setTaskId(taskId);
							updateWorkFlowBotStatus(workflowBotStepByStatus, StatusWorkflow.WAITING);							
						}
					}
				}
			}
		}
	}

	private boolean canContinueTaskWorkflow(Task task, WorkflowBot workflowBot){
		boolean hasTaksPendents = false;
		Long taskId = null;
		if(verifyTaskWorkflowProcessed(task)){
			String fieldVerify = workflowBot.getWorkflowBotStep().getFieldVerify();
			List<String> status = Lists.newArrayList(StatusProcessItemTaskEnum.SUCCESS.getName());
			List<ItemTask> list = taskManager.itemTaskListStatus(status , task.getId());
			if(CollectionUtils.isNotEmpty(list)){
				for(ItemTask itemTask : list){
					String dataOut = itemTask.getDataOut();
					Pattern compile = Pattern.compile(fieldVerify);
					Matcher matcher = compile.matcher(dataOut);
					boolean matches = matcher.find();
					if(!matches){
						logger.info("RC não aprovada da Item Task Id: {}",itemTask.getId());
						hasTaksPendents = true;
						taskId = itemTask.getTask().getId();
						taskManager.updateItemTask(itemTask.getId(), StatusProcessItemTaskEnum.AVAILABLE);						
					}
				}
				if(hasTaksPendents){
					taskManager.updateTask(taskId, StatusProcessTaskEnum.PROCESSING);
					return false;
				}else{
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean verifyTaskWorkflowProcessed(Task task) {
		return null != task && task.getStatusProcess().getName().equals(StatusProcessTaskEnum.PROCESSED.getName());
	}

	private WorkflowBot updateWorkFlowBotStatus(WorkflowBot workflowBot, StatusWorkflow statusWorkflow) {
		if (statusWorkflow == StatusWorkflow.COMPLETE) {
			workflowBot.setDateUpdated(LocalDate.now());
		}
		workflowBot.setStatus(statusWorkflow);
		workflowBot = workFlowBotRepository.save(workflowBot);
		return workflowBot;
	}
}