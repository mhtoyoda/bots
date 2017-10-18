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
public class CanceledTask {

	@Autowired
	private TaskManager taskManager;
	
	@Autowired
	private BrokerManager brokerManager;

	@Autowired
	private ServiceElasticSearch serviceElasticSearch;
	
	public void applyUpdateTaskCanceled() {
		List<Task> tasks = taskManager.allTaskProcessing(StatusProcessTaskEnum.CANCELED.getName());
		tasks.stream().forEach(task -> {
			if(null == task.getEndAt()){
				String queueName = String.format("%s.%s.IN", task.getBot().getNameBot(), task.getId());
				deleteQueueCanceled(queueName);
				updateItemTaskCanceled(task);

				serviceElasticSearch.log(task,task.getId());
			}
		});
	}

	private void updateItemTaskCanceled(Task task) {
		List<String> statusList = Lists.newArrayList(StatusProcessItemTaskEnum.INLINE.getName());
		List<ItemTask> itemTaskList = taskManager.itemTaskListStatus(statusList, task.getId());
		itemTaskList.stream().forEach(itemTask -> {
			taskManager.updateItemTask(itemTask.getId(), StatusProcessItemTaskEnum.CANCELED);
		});
		taskManager.updateTask(task.getId(), StatusProcessTaskEnum.CANCELED);
	}
	
	private void deleteQueueCanceled(String queueName){			
		brokerManager.deleteQueue(queueName);
	}
}