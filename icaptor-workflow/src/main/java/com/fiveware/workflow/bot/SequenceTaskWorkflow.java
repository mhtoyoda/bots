package com.fiveware.workflow.bot;

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
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SequenceTaskWorkflow {

	@Autowired
	private TaskManager taskManager;

	@Autowired
	private WorkFlowBotRepository workFlowBotRepository;

	@Autowired
	private ProcessWorkflowTask processWorkflowTask;

	public void sequenceTask() {
		Optional<WorkflowBot> optional = Optional.fromNullable(workFlowBotRepository.findByStatus(StatusWorkflow.WAITING));
		if (optional.isPresent()) {
			WorkflowBot workflowBot = optional.get();
			Task task = taskManager.getTask(workflowBot.getTaskId());
			if (canContinueTaskWorkflow(task, workflowBot)) {
				if (null != workflowBot.getWorkflowBotStep() && StringUtils.isNotBlank(workflowBot.getWorkflowBotStep().getBotTarget())) {
					Long taskId = processWorkflowTask.createTaskByWorkflow(workflowBot);
					if (null != taskId) {
						WorkflowBot workflowBotStepByStatus = workFlowBotRepository.findWorkflowBotStepByStatus(workflowBot.getWorkflowBotStep().getBotTarget(), StatusWorkflow.NEW);
						if (null != workflowBotStepByStatus) {
							updateWorkFlowBotStatus(workflowBot, StatusWorkflow.COMPLETE);
							updateWorkFlowBotStatus(workflowBotStepByStatus, StatusWorkflow.WAITING);
						}
					}
				}
			}else if(!verifyTaskNotProcessingWorkflow(task)){			
				List<ItemTask> list = taskManager.itemTaskListStatus(Lists.newArrayList(StatusProcessItemTaskEnum.ERROR.getName()), workflowBot.getTaskId());
				java.util.Optional<ItemTask> findFirst = list.stream().findFirst();
				if(findFirst.isPresent()){
					ItemTask itemTask = findFirst.get();
					Task actualTask = taskManager.createTask(workflowBot.getWorkflowBotStep().getBotSource(), 1L);
					taskManager.createItemTask(actualTask, itemTask.getDataOut());
					taskManager.updateTask(actualTask.getId(), StatusProcessTaskEnum.PROCESSING);
				}
			}
		}
	}

	private boolean canContinueTaskWorkflow(Task task, WorkflowBot workflowBot){
		if(verifyTaskWorkflow(task)){
			String fieldVerify = workflowBot.getWorkflowBotStep().getFieldVerify();
			List<String> status = Lists.newArrayList(StatusProcessItemTaskEnum.SUCCESS.getName());
			List<ItemTask> list = taskManager.itemTaskListStatus(status , task.getId());
			if(CollectionUtils.isNotEmpty(list)){
				ItemTask itemTask = list.get(0);
				String dataOut = itemTask.getDataOut();
				Pattern compile = Pattern.compile(fieldVerify);
				Matcher matcher = compile.matcher(dataOut);
				boolean matches = matcher.find();
				return matches;
			}
		}
		return false;
	}
	
	private boolean verifyTaskWorkflow(Task task) {
		return null != task && task.getStatusProcess().getName().equals(StatusProcessTaskEnum.PROCESSED.getName());
	}
	
	private boolean verifyTaskNotProcessingWorkflow(Task task) {
		return null != task && task.getStatusProcess().getName().equals(StatusProcessTaskEnum.PROCESSING.getName());
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