package com.fiveware.task.status;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.fiveware.model.Task;

public class TaskStatusCurrent {

	private static Map<String, TaskStatusStep> taskStatusMap;
	
	@PostConstruct
	public static void init(Task task) {
		taskStatusMap = new HashMap<String, TaskStatusStep>();
		taskStatusMap.put(TaskStatus.START.name(), new TaskStart(task));
		taskStatusMap.put(TaskStatus.CREATED.name(), new TaskCreated(task));
		taskStatusMap.put(TaskStatus.SCHEDULED.name(), new TaskScheduled(task));
		taskStatusMap.put(TaskStatus.PAUSE.name(), new TaskPause(task));
		taskStatusMap.put(TaskStatus.ERROR.name(), new TaskError(task));
		taskStatusMap.put(TaskStatus.CONSOLIDATING.name(), new TaskConsolidating(task));
		taskStatusMap.put(TaskStatus.STOP.name(), new TaskStop(task));
	}

	public static TaskStatusStep getStatusOf(Task task) {
		if( null == taskStatusMap){			
			init(task);
		}
		return taskStatusMap.get(task.getStatus());
	}
}
