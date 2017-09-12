package com.fiveware.task;

import com.fiveware.model.ItemTask;
import com.fiveware.model.StatusProcessItemTaskEnum;
import com.fiveware.model.StatusProcessTaskEnum;
import com.fiveware.model.Task;
import com.fiveware.model.message.MessageBot;
import com.fiveware.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Component
public class TaskManager {

	@Autowired
	private ServiceTask serviceTask;

	@Autowired
	private ServiceItemTask itemServiceTask;

	@Autowired
	private ServiceStatusProcessTask serviceStatusProcessTask;

	@Autowired
	private ServiceUser serviceUser;

	@Autowired
	private ServiceBot serviceBot;

	public Task createTask(String nameBot, Long userId) {
		Task task = new Task();
		task.setBot(serviceBot.findByNameBot(nameBot).get());
		task.setLoadTime(new Date());
		task.setStatusProcess(serviceStatusProcessTask.getStatusProcessById(StatusProcessTaskEnum.CREATED.getId()));
		task.setStartAt(new Date());
		task.setUsuario(serviceUser.getUserById(userId).get());
		task = serviceTask.save(task);
		return task;
	}

	public Task updateTask(Long taskId, StatusProcessTaskEnum statuProcessEnum) {
		Task task = serviceTask.getTaskById(taskId);
		task.setStatusProcess(statuProcessEnum.getStatuProcess());
		if (statuProcessEnum.equals(StatusProcessTaskEnum.ERROR) || statuProcessEnum.equals(StatusProcessTaskEnum.PROCCESSED)) {
			task.setEndAt(new Date());
		}
		task = serviceTask.save(task);
		return task;
	}

	public ItemTask createItemTask(Task task, String recordLine) {
		ItemTask itemTask = new ItemTask();
		itemTask.setTask(task);		
		itemTask.setDataIn(recordLine);
		itemTask.setStatusProcess(serviceStatusProcessTask.getStatusProcessItemTaskById(StatusProcessItemTaskEnum.AVAILABLE.getId()));
		itemTask = itemServiceTask.save(itemTask);
		return itemTask;
	}

	public ItemTask updateItemTask(Long itemTaskId, StatusProcessItemTaskEnum statuProcessEnum, String dataOut) {
		ItemTask itemTask = itemServiceTask.getItemTaskById(itemTaskId);
		itemTask.setStatusProcess(statuProcessEnum.getStatuProcess());
		if (StringUtils.isNotBlank(dataOut)) {
			itemTask.setDataOut(dataOut);
		}
		if (statuProcessEnum.equals(StatusProcessTaskEnum.ERROR) || statuProcessEnum.equals(StatusProcessTaskEnum.SUCCESS)) {
			itemTask.setEndAt(new Date());
		}
		if (statuProcessEnum.equals(StatusProcessTaskEnum.PROCESSING)) {
			int attemptsCount = itemTask.getAttemptsCount() == null ? 0 : itemTask.getAttemptsCount() + 1;
			itemTask.setAttemptsCount(attemptsCount);
		}
		itemTask = itemServiceTask.save(itemTask);
		return itemTask;
	}
	
	public ItemTask updateItemTask(Long itemTaskId, StatusProcessItemTaskEnum statusProcessItemTaskEnum){
		ItemTask itemTask = itemServiceTask.getItemTaskById(itemTaskId);
		itemTask.setStatusProcess(statusProcessItemTaskEnum.getStatuProcess());
		
		if (statusProcessItemTaskEnum.equals(StatusProcessItemTaskEnum.PROCESSING)) {
			itemTask.setStartAt(new Date());
			int attemptsCount = itemTask.getAttemptsCount() == null ? 0 : itemTask.getAttemptsCount() + 1;
			itemTask.setAttemptsCount(attemptsCount);
		}		
		itemTask = itemServiceTask.save(itemTask);
		return itemTask;
	}

	public Task getTask(Long taskId) {
		return serviceTask.getTaskById(taskId);
	}

	public ItemTask updateItemTask(MessageBot messageBot) {
		ItemTask itemTask = itemServiceTask.getItemTaskById(messageBot.getItemTaskId());

		if (!Objects.isNull(messageBot.getStatusProcessItemTaskEnum())){

			if (StatusProcessItemTaskEnum.ERROR.equals(messageBot.getStatusProcessItemTaskEnum()) ||
					StatusProcessItemTaskEnum.SUCCESS.equals(messageBot.getStatusProcessItemTaskEnum())) {
				itemTask.setEndAt(new Date());

			}
			if (messageBot.getStatusProcessItemTaskEnum().equals(StatusProcessItemTaskEnum.PROCESSING)) {
				int attemptsCount = itemTask.getAttemptsCount() == null ? 0 : itemTask.getAttemptsCount() + 1;
				itemTask.setAttemptsCount(attemptsCount);
			}
		}

		itemTask.setAttemptsCount(messageBot.getAttemptsCount());
		itemTask.setStatusProcess(messageBot.getStatusProcessItemTaskEnum().getStatuProcess());
		itemTask.setDataOut(messageBot.getLineResult());

		itemTask = itemServiceTask.save(itemTask);
		return itemTask;
	}

	public List<ItemTask> allItemTaskProcessing(String status){
		List<ItemTask> list = itemServiceTask.getItemTaskByStatus(status);
		return list;
	}
	
	public List<Task> allTaskProcessing(String status){
		List<Task> list = serviceTask.getTaskByStatus(status);
		return list;
	}
	
	public List<ItemTask> itemTaskListStatus(List<String> status, Long taskId){
		List<ItemTask> list = itemServiceTask.getItemTaskByListStatus(status, taskId);
		return list;
	}
	
	public Long getItemTaskCountByTask(Long taskId){
		return itemServiceTask.getItemTaskCountByTask(taskId);
	}
}