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
public class SuspendedTask {

	@Autowired
	private TaskManager taskManager;
	
	@Autowired
	private BrokerManager brokerManager;

	@Autowired
	private ServiceElasticSearch serviceElasticSearch;
	
	public void applyUpdateTaskSuspending() {
		List<Task> tasks = taskManager.allTaskProcessing(StatusProcessTaskEnum.SUSPENDING.getName());
		tasks.stream().forEach(task -> {
			String queueName = String.format("%s.%s.IN", task.getBot().getNameBot(), task.getId());
			deleteQueueSuspending(queueName);
			updateItemTaskSuspended(task);
			serviceElasticSearch.log(task,task.getId());
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
	
	private void deleteQueueSuspending(String queueName){
		brokerManager.deleteQueue(queueName);
	}

}
