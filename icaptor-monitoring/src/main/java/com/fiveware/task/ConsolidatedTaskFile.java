package com.fiveware.task;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fiveware.model.ItemTask;
import com.fiveware.model.StatusProcessItemTaskEnum;
import com.fiveware.model.StatusProcessTaskEnum;
import com.fiveware.model.Task;
import com.fiveware.model.TaskFile;
import com.fiveware.service.ServiceTask;
import com.google.common.collect.Lists;

@Component
public class ConsolidatedTaskFile {

	@Autowired
	private ServiceTask serviceTask;
	
	@Autowired
	private TaskFileManager taskFileManager;
	
	@Autowired
	private TaskManager taskManager;
	
	public void consolidatedFileTask(){
		processFileTaskProcessed();		
		processFileTaskSuspend();
	}

	private void processFileTaskProcessed() {
		List<Task> taskProcessed = serviceTask.getTaskRecentByStatus(StatusProcessTaskEnum.PROCESSED.getName());
		taskProcessed.forEach(task -> {
			List<TaskFile> taskFileList = taskFileManager.getFileTaskById(task.getId());
			if(CollectionUtils.isEmpty(taskFileList)){
				List<String> status = Lists.newArrayList(StatusProcessItemTaskEnum.SUCCESS.getName());
				List<ItemTask> itemTaskList = taskManager.itemTaskListStatus(status, task.getId());
				taskFileManager.processTaskFile(task, itemTaskList);
			}
		});
	}

	private void processFileTaskSuspend() {
		List<Task> taskPaused = serviceTask.getTaskRecentByStatus(StatusProcessTaskEnum.SUSPENDED.getName());
		taskPaused.forEach(task -> {
			List<TaskFile> taskFileList = taskFileManager.getFileTaskById(task.getId());
			if(CollectionUtils.isNotEmpty(taskFileList)){
				taskFileList.forEach(taskFile -> {
					taskFileManager.deleteTaskFileManager(taskFile);					
				});
			}
							
			List<String> status = Lists.newArrayList(StatusProcessItemTaskEnum.SUCCESS.getName());				
			List<ItemTask> itemTaskList = taskManager.itemTaskListStatus(status, task.getId());				
			taskFileManager.processTaskFile(task, itemTaskList);
		});
	}
}