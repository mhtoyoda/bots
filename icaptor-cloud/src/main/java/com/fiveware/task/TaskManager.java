package com.fiveware.task;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fiveware.exception.BotNotFoundException;
import com.fiveware.messaging.TypeMessage;
import com.fiveware.model.Bot;
import com.fiveware.model.BotParameterKeyValue;
import com.fiveware.model.Task;
import com.fiveware.parameter.BotParameter;
import com.fiveware.service.ServiceBot;
import com.fiveware.service.ServiceTask;
import com.fiveware.task.status.TaskStatus;

@Component
public class TaskManager {

	@Autowired
	private ServiceTask taskService;
	
	@Autowired
	private ServiceBot serviceBot;
	
	@Autowired
	private BotParameter botParameter;
	
	public TaskMessageBot createTask(TaskStatus taskStatus, String botName, Integer qtdInstances) throws BotNotFoundException{
		Task task = createNewTask(taskStatus, botName);	
		List<Map<String,BotParameterKeyValue>> parameters = loadParameters(botName);
		TaskMessageBot taskMessageBot = new TaskMessageBot(task.getId(), qtdInstances , parameters, TypeMessage.TASK_FILE);
		return taskMessageBot;
	}
	
	private Task createNewTask(TaskStatus taskStatus, String botName) throws BotNotFoundException {
		Optional<Bot> bot = serviceBot.findByNameBot(botName);
		if(bot.isPresent()){
			Task task = new Task();
			task.setBot(bot.get());
			task.setCreateTime(Date.from(Instant.now()));
			task.setLastUpdateTime(Date.from(Instant.now()));
			task.setStatus(taskStatus.name());
			task = taskService.save(task);
			return task;
		}else{
			throw new BotNotFoundException(String.format("Bot %s not found!", botName));
		}		
	}
	
	private List<Map<String, BotParameterKeyValue>> loadParameters(String nameBot){
		List<Map<String, BotParameterKeyValue>> parameters = botParameter.loadParameterBot(nameBot);
		return parameters;
	}
}
