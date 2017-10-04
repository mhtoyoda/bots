package com.fiveware.workflow.bot;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fiveware.model.ItemTask;
import com.fiveware.model.StatusProcessItemTaskEnum;
import com.fiveware.model.StatusProcessTaskEnum;
import com.fiveware.model.Task;
import com.fiveware.task.TaskManager;
import com.fiveware.workflow.exception.TaskCreateException;
import com.fiveware.workflow.model.WorkflowBot;
import com.fiveware.workflow.model.WorkflowBotStep;
import com.google.common.collect.Lists;

@Component
public class ProcessWorkflowTask implements WorkflowSequence {
	
	@Autowired
	private TaskManager taskManager;
	
	@Override
	public Long createTask(WorkflowBotStep workflowBotStep) throws TaskCreateException {
		try{
			Task createTask = taskManager.createTask(workflowBotStep.getBotSource(), 1L);
			taskManager.createItemTask(createTask, "");
			taskManager.updateTask(createTask.getId(), StatusProcessTaskEnum.PROCESSING);
			return createTask.getId();			
		}catch (Exception e) {
			throw new TaskCreateException(e.getMessage());
		}
	}
	
	@Override
	public Long createTaskByWorkflow(WorkflowBot workflowBot) {		
		Task createTask = null;
		List<ItemTask> list = taskManager.itemTaskListStatus(Lists.newArrayList(StatusProcessItemTaskEnum.SUCCESS.getName()), workflowBot.getTaskId());
		if(CollectionUtils.isNotEmpty(list)){
			createTask = taskManager.createTask(workflowBot.getWorkflowBotStep().getBotTarget(), 1L);			
			for (ItemTask itemTask : list) {									
				taskManager.createItemTask(createTask, itemTask.getDataOut());			
			}
			createTask = taskManager.updateTask(createTask.getId(), StatusProcessTaskEnum.PROCESSING);
			return createTask.getId();
		}
		return null;
	}
}