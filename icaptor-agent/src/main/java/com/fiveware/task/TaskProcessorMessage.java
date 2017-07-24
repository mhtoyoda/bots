package com.fiveware.task;

import com.fiveware.exception.AttributeLoadException;
import com.fiveware.exception.ExceptionBot;
import com.fiveware.messaging.QueueName;
import com.fiveware.messaging.Receiver;
import com.fiveware.model.Bot;
import com.fiveware.processor.ProcessBot;
import com.fiveware.pulling.BrokerPulling;
import com.fiveware.service.ServiceAgent;
import com.fiveware.service.ServiceTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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
	public boolean canPullingMessage(String queue) {
		//TODO verificar status da task
		return true;
	}

	@Override
	public void processMessage(String botName, TaskMessageBot obj) {
		try {
			//TODO atualizar status da task
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
}
