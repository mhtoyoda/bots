package com.fiveware.service;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fiveware.exception.MessageStatusBot;
import com.fiveware.messaging.Producer;
import com.fiveware.task.TaskManager;
import com.fiveware.task.TaskMessageBot;
import com.fiveware.task.status.TaskStatus;

@RestController
@RequestMapping("/api")
public class TaskService {

	static Logger logger = LoggerFactory.getLogger(ServiceTask.class);
	
	@Autowired
	private TaskManager taskManager;
	
	@Autowired
    @Qualifier("taskMessageProducer")
    private Producer<TaskMessageBot> producer;
	
	@GetMapping("task/create/{botName}")
	public ResponseEntity<Object> taskCreate(@PathVariable String botName, 
			@RequestParam(name = "qtdInstances", defaultValue = "1") Integer qtdInstances,
			HttpServletRequest httpRequest) {
		try {			
			TaskMessageBot taskMessageBot = taskManager.createTask(TaskStatus.CREATED, botName, qtdInstances);
			producer.send(botName+"_IN", taskMessageBot);
			return ResponseEntity.ok().body("ID TASK CREATED: "+taskMessageBot.getTaskId());                    
		} catch (Exception e) {
			  return ResponseEntity.badRequest().
	                    body(new MessageStatusBot(HttpStatus.BAD_REQUEST.value(),e.getMessage()));
		}
	}
}