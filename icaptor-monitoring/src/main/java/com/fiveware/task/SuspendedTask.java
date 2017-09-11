package com.fiveware.task;

import java.util.List;

import com.fiveware.model.StatusProcessItemTaskEnum;
import com.fiveware.model.StatusProcessTaskEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fiveware.model.ItemTask;
import com.fiveware.model.Task;
import com.google.common.collect.Lists;

@Component
public class SuspendedTask {

	@Autowired
	private TaskManager taskManager;

	public void applyUpdateTaskSuspending() {
		List<Task> tasks = taskManager.allTaskProcessing(StatusProcessTaskEnum.SUSPENDING.getName());
		tasks.stream().forEach(task -> {
			updateItemTaskSuspended(task);
		});
	}

	private void updateItemTaskSuspended(Task task) {
		List<String> statusList = Lists.newArrayList(StatusProcessItemTaskEnum.INLINE.getName());
		List<ItemTask> itemTaskList = taskManager.itemTaskListStatus(statusList, task.getId());
		itemTaskList.stream().forEach(itemTask -> {
			taskManager.updateItemTask(itemTask.getId(), StatusProcessItemTaskEnum.AVAILABLE);
		});
		taskManager.updateTask(task.getId(), StatusProcessTaskEnum.SUSPENDED);
	}
}
