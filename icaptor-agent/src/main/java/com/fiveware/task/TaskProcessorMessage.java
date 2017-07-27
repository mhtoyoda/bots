package com.fiveware.task;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fiveware.exception.AttributeLoadException;
import com.fiveware.exception.ExceptionBot;
import com.fiveware.messaging.QueueName;
import com.fiveware.messaging.Receiver;
import com.fiveware.model.Bot;
import com.fiveware.model.Task;
import com.fiveware.processor.ProcessBot;
import com.fiveware.pulling.BrokerPulling;
import com.fiveware.service.ServiceAgent;
import com.fiveware.service.ServiceTask;
import com.fiveware.task.status.TaskStatus;

@Component("taskProcessorMessage")
public class TaskProcessorMessage extends BrokerPulling<TaskMessageBot> {

	private static Logger log = LoggerFactory.getLogger(TaskProcessorMessage.class);
	
	@Value("${agent}")
	private String nameAgent;
	
	@Autowired
	@Qualifier("taskMessageReceiver")
	private Receiver<TaskMessageBot> receiver;
	
	@Autowired
	@Qualifier("processBotFile")
	private ProcessBot<TaskMessageBot> processBotFile;
	
	@Autowired
	private ServiceAgent serviceAgent;
	
	@Autowired
	private ServiceTask serviceTask;
	
	//@Scheduled(fixedDelayString = "${broker.queue.send.schedularTime}")
	public void process(){
		List<Bot> bots = serviceAgent.findBotsByAgent(nameAgent);
		bots.forEach(bot -> {
			String botName = bot.getNameBot();
			String nameQueue = QueueName.TASKS.name();			 
			pullMessage(botName, nameQueue);
		});
	}
	
	@Override
	public boolean canPullingMessage() {
		return true;
	}

	@Override
	public void processMessage(String botName, TaskMessageBot obj) {
		try {
			Task task = serviceTask.getTaskById(obj.getTaskId());			
			Optional<Task> taskOptional = Optional.of(task);
			taskOptional.ifPresent(taskPresent ->{
				taskPresent = controlStatusTask(taskPresent);
				serviceTask.save(taskPresent);
			});
			
			processBotFile.execute(botName, obj);
			log.debug("[BOT]: {}", botName);
		} catch (ClassNotFoundException | IOException | AttributeLoadException | ExceptionBot e) {
			log.error("Error - {}", e.getMessage());
		}
	}

	@Override
	public Optional<TaskMessageBot> receiveMessage(String queueName) {		
		return Optional.ofNullable(receiver.receive(queueName));
	}

	private Task controlStatusTask(Task task) {
		if(task.getStatus().equals(TaskStatus.CREATED.name())){
			task.getStatusTask().start();
		}
		if(task.getStatus().equals(TaskStatus.PAUSE.name())){
			task.getStatusTask().restart();
		}
		if(task.getStatus().equals(TaskStatus.STOP.name())){
			task.getStatusTask().restart();
		}
		if(task.getStatus().equals(TaskStatus.ERROR.name())){
			task.getStatusTask().restart();
		}
		return task;
	}
}