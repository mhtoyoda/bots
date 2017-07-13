package com.fiveware.task;

import com.fiveware.exception.BotNotFoundException;
import com.fiveware.messaging.Producer;
import com.fiveware.messaging.QueueName;
import com.fiveware.messaging.TypeMessage;
import com.fiveware.model.BotParameterKeyValue;
import com.fiveware.model.MessageBot;
import com.fiveware.model.MessageHeader;
import com.fiveware.model.entities.Bot;
import com.fiveware.model.entities.Task;
import com.fiveware.parameter.BotParameter;
import com.fiveware.service.ServiceBot;
import com.fiveware.service.ServiceTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class TaskManager {

	@Autowired
	private ServiceTask taskService;
	
	@Autowired
	private ServiceBot serviceBot;
	
	@Autowired
    @Qualifier("taskMessageProducer")
    private Producer<MessageBot> producer;
	
	@Autowired
	private BotParameter botParameter;
	
	public Task createTask(TaskStatus taskStatus, String botName, Integer qtdInstances) throws BotNotFoundException{
		Task task = createNewTask(taskStatus, botName);
		MessageHeader messageHeader = new MessageHeader
                .MessageHeaderBuilder("", 0)
                .chuncksInitial(0)
                .chuncksEnd(0)
                .timeStamp(System.currentTimeMillis())
                .build();
		MessageBot messageBot = new MessageBot(TypeMessage.TASK, "Task Created", messageHeader);
		producer.send(QueueName.TASKS.name(), messageBot);
		return task;
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
