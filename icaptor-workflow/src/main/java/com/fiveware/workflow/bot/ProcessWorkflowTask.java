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
import com.fiveware.workflow.parse.SplitJson;
import com.google.common.collect.Lists;

@Component
public class ProcessWorkflowTask implements WorkflowSequence {
	
	@Autowired
	private TaskManager taskManager;
	
	@Autowired
	private SplitJson splitJson;
	
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
		List<ItemTask> list = taskManager.itemTaskListStatus(Lists.newArrayList(StatusProcessItemTaskEnum.SUCCESS.getName()), workflowBot.getTaskId());
		if(CollectionUtils.isNotEmpty(list)){
			return createItemTask(workflowBot, list);
		}
		return null;
	}

	private Long createTaskWorkflowByitemTask(WorkflowBot workflowBot, List<ItemTask> list) {
		Task createTask = taskManager.createTask(workflowBot.getWorkflowBotStep().getBotTarget(), 1L);			
		for (ItemTask itemTask : list) {									
			taskManager.createItemTask(createTask, itemTask.getDataOut());			
		}
		createTask = taskManager.updateTask(createTask.getId(), StatusProcessTaskEnum.PROCESSING);
		return createTask.getId();
	}
	
	private Long createItemTask(WorkflowBot workflowBot, List<ItemTask> list){
		if(null != workflowBot.getWorkflowBotStep() && workflowBot.getWorkflowBotStep().getBotTarget().equals("votorantimRC")){
			Task task = taskManager.createTask(workflowBot.getWorkflowBotStep().getBotTarget(), 1L);
			for (ItemTask itemTask : list) {
				List<String> arrayJson = splitJson.splitArray(itemTask.getDataOut());
				for (String json : arrayJson) {
					System.out.println("JSON "+json);
					taskManager.createItemTask(task, json);
				}
			}
			task = taskManager.updateTask(task.getId(), StatusProcessTaskEnum.PROCESSING);
			return task.getId();
		}else{
			return createTaskWorkflowByitemTask(workflowBot, list);
		}
	}
}