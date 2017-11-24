package com.fiveware.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fiveware.messaging.BrokerManager;
import com.fiveware.model.ItemTask;
import com.fiveware.model.StatusProcessItemTaskEnum;
import com.fiveware.model.StatusProcessTaskEnum;
import com.fiveware.model.Task;
import com.google.common.collect.Lists;

@Component
public class TaskProcessedTask {

	@Autowired
	private TaskManager taskManager;
	
	@Autowired
	private BrokerManager brokerManager;

	
	public void checkTaskProcessed() {
		List<Task> taskProcessing = taskManager.allTaskProcessing(StatusProcessTaskEnum.PROCESSING.getName());
		taskProcessing.stream().forEach(task -> {
			Long countItemTask = countItemTask(task);
			Long countItemTaskErrorOrSucess = countItemTaskProcessing(task);
			if (countItemTask == countItemTaskErrorOrSucess) {
				String queueName = String.format("%s.%s.IN", task.getBot().getNameBot(), task.getId());
				taskManager.updateTask(task.getId(), StatusProcessTaskEnum.PROCESSED);
				brokerManager.deleteQueue(queueName);

			}
		});
	}

	private Long countItemTaskProcessing(Task task) {
		List<String> status = Lists.newArrayList(StatusProcessItemTaskEnum.ERROR.getName(),
				StatusProcessItemTaskEnum.SUCCESS.getName());
		List<ItemTask> itemTaskListStatus = taskManager.itemTaskListStatus(status, task.getId());
		return itemTaskListStatus.stream().count();
	}

	private Long countItemTask(Task task) {
		Long itemTaskCountByTask = taskManager.getItemTaskCountByTask(task.getId());
		return itemTaskCountByTask;
	}
}
