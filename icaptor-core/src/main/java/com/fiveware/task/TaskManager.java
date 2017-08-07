package com.fiveware.task;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fiveware.model.ItemTask;
import com.fiveware.model.Task;
import com.fiveware.service.ServiceBot;
import com.fiveware.service.ServiceItemTask;
import com.fiveware.service.ServiceStatusProcessTask;
import com.fiveware.service.ServiceTask;
import com.fiveware.service.ServiceUser;

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
		task.setStatusProcess(serviceStatusProcessTask.getStatusProcessById(StatuProcessEnum.CREATED.getId()));
		task.setStartAt(new Date());
		task.setUsuario(serviceUser.getUserById(userId));
		task = serviceTask.save(task);
		return task;
	}

	public Task updateTask(Long taskId, StatuProcessEnum statuProcessEnum) {
		Task task = serviceTask.getTaskById(taskId);
		task.setStatusProcess(serviceStatusProcessTask.getStatusProcessById(statuProcessEnum.getId()));
		if (statuProcessEnum.equals(StatuProcessEnum.ERROR) || statuProcessEnum.equals(StatuProcessEnum.SUCCESS)) {
			task.setEndAt(new Date());
		}
		return task;
	}

	public ItemTask createItemTask(Task task, String recordLine) {
		ItemTask itemTask = new ItemTask();
		itemTask.setTask(task);
		itemTask.setDataIn(recordLine);
		itemTask.setStatusProcess(serviceStatusProcessTask.getStatusProcessById(StatuProcessEnum.AVAILABLE.getId()));
		itemTask = itemServiceTask.save(itemTask);
		return itemTask;
	}

	public ItemTask updateItemTask(Long itemTaskId, StatuProcessEnum statuProcessEnum, String dataOut) {
		ItemTask itemTask = itemServiceTask.getItemTaskById(itemTaskId);
		itemTask.setStatusProcess(serviceStatusProcessTask.getStatusProcessById(statuProcessEnum.getId()));
		if (StringUtils.isNotBlank(dataOut)) {
			itemTask.setDataOut(dataOut);
		}
		if (statuProcessEnum.equals(StatuProcessEnum.ERROR) || statuProcessEnum.equals(StatuProcessEnum.SUCCESS)) {
			itemTask.setEndAt(new Date());
		}
		if (statuProcessEnum.equals(StatuProcessEnum.PROCESSING)) {
			int attemptsCount = itemTask.getAttemptsCount() == null ? 0 : itemTask.getAttemptsCount() + 1;
			itemTask.setAttemptsCount(attemptsCount);
		}
		return itemTask;
	}
}
