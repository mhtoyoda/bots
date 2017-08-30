package com.fiveware.task;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fiveware.model.ItemTask;
import com.fiveware.model.Parameter;
import com.fiveware.model.Task;
import com.fiveware.parameter.ParameterResolver;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;

@Component
public class TaskResolver {

	@Autowired
	private TaskManager taskManager;

	@Autowired
	private ParameterResolver parameterResolver;

	public void process() {				
		checkTimeout();		
		checkTaskProcessed();
	}

	private void checkTaskProcessed() {
		List<Task> taskProcessing = taskManager.allTaskProcessing(StatusProcessTaskEnum.PROCESSING.getName());
		taskProcessing.stream().forEach(task ->{
			Long countItemTask = countItemTask(task);
			Long countItemTaskErrorOrSucess = countItemTaskProcessing(task);
			if(countItemTask == countItemTaskErrorOrSucess){
				taskManager.updateTask(task.getId(), StatusProcessTaskEnum.PROCCESSED);
			}
		});
	}

	private Long countItemTaskProcessing(Task task){
		List<String> status = Lists.newArrayList(StatusProcessItemTaskEnum.ERROR.getName(), StatusProcessItemTaskEnum.SUCCESS.getName());
		List<ItemTask> itemTaskListStatus = taskManager.itemTaskListStatus(status, task.getId());
		return itemTaskListStatus.stream().count();
	}
	
	private Long countItemTask(Task task){
		Long itemTaskCountByTask = taskManager.getItemTaskCountByTask(task.getId());
		return itemTaskCountByTask;
	}
	
	private void checkTimeout() {
		int timeout = getTimeoutParameter();
		List<ItemTask> itemTaskProcessing = taskManager.allItemTaskProcessing(StatusProcessItemTaskEnum.PROCESSING.getName());
		itemTaskProcessing.stream().forEach(item -> {
			Instant instantStartAt = Instant.ofEpochMilli(item.getStartAt().getTime());
			LocalTime startAt = LocalDateTime.ofInstant(instantStartAt, ZoneId.systemDefault()).toLocalTime();
			LocalTime now = LocalTime.now();
			long secondsDuration = Duration.between(startAt, now).getSeconds();
			if (secondsDuration > timeout) {
				taskManager.updateItemTask(item.getId(), StatusProcessItemTaskEnum.ERROR);
			}
		});
	}

	private int getTimeoutParameter() {
		Parameter parameterCloud = parameterResolver.getParameterCloud("timeout");
		Optional<Parameter> optional = Optional.fromNullable(parameterCloud);
		if (optional.isPresent()) {
			Parameter parameter = optional.get();
			return Integer.parseInt(parameter.getFieldValue());
		}
		return 15;
	}
}
