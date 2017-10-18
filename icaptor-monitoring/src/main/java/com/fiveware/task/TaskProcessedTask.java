package com.fiveware.task;

import com.fiveware.messaging.BrokerManager;
import com.fiveware.model.ItemTask;
import com.fiveware.model.StatusProcessItemTaskEnum;
import com.fiveware.model.StatusProcessTaskEnum;
import com.fiveware.model.Task;
import com.fiveware.service.ServiceElasticSearch;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskProcessedTask {

	@Autowired
	private TaskManager taskManager;
	
	@Autowired
	private BrokerManager brokerManager;
	@Autowired
	private ServiceElasticSearch serviceElasticSearch;
	
	public void checkTaskProcessed() {
		List<Task> taskProcessing = taskManager.allTaskProcessing(StatusProcessTaskEnum.PROCESSING.getName());
		taskProcessing.stream().forEach(task -> {
			Long countItemTask = countItemTask(task);
			Long countItemTaskErrorOrSucess = countItemTaskProcessing(task);
			if (countItemTask == countItemTaskErrorOrSucess) {
				String queueName = String.format("%s.%s.IN", task.getBot().getNameBot(), task.getId());
				taskManager.updateTask(task.getId(), StatusProcessTaskEnum.PROCESSED);
				serviceElasticSearch.log(task);
				brokerManager.deleteQueue(queueName);

				serviceElasticSearch.log(task,task.getId());

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
