package com.fiveware.workflow.bot;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fiveware.model.ItemTask;
import com.fiveware.model.StatusProcessItemTaskEnum;
import com.fiveware.model.StatusProcessTaskEnum;
import com.fiveware.model.Task;
import com.fiveware.task.TaskManager;
import com.fiveware.workflow.model.WorkflowBot;
import com.fiveware.workflow.model.WorkflowBotStep;
import com.google.common.collect.Lists;

@Component
public class ProcessWorkflowTask implements WorkflowSequence {
	
	@Autowired
	private TaskManager taskManager;
	
	@Override
	public Long createTask(WorkflowBotStep workflowBotStep) {
		Task createTask = taskManager.createTask(workflowBotStep.getBotSource(), 1L);
		taskManager.createItemTask(createTask, "");
		taskManager.updateTask(createTask.getId(), StatusProcessTaskEnum.PROCESSING);
		return createTask.getId();
	}
	
	@Override
	public Long createTaskByWorkflow(WorkflowBot workflowBot) {		
		List<ItemTask> list = taskManager.itemTaskListStatus(Lists.newArrayList(StatusProcessItemTaskEnum.SUCCESS.getName()), workflowBot.getTaskId());
		Optional<ItemTask> findFirst = list.stream().findFirst();
		if(findFirst.isPresent()){
			ItemTask itemTask = findFirst.get();
			Task createTask = taskManager.createTask(workflowBot.getWorkflowBotStep().getBotTarget(), 1L);
			taskManager.createItemTask(createTask, itemTask.getDataOut());
			taskManager.updateTask(createTask.getId(), StatusProcessTaskEnum.PROCESSING);
			return createTask.getId();
		}
		return null;
	}
}