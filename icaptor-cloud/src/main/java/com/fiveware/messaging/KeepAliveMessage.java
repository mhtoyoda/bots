package com.fiveware.messaging;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fiveware.config.ServerConfig;
import com.fiveware.model.Agent;
import com.fiveware.model.Server;
import com.fiveware.model.Task;
import com.fiveware.model.message.MessageAgent;
import com.fiveware.model.message.MessageTask;
import com.fiveware.service.ServiceAgent;
import com.fiveware.service.ServiceTask;
import com.fiveware.task.StatusProcessTaskEnum;

@Component("KEEP_ALIVE")
public class KeepAliveMessage implements ConsumerTypeMessage<MessageAgent> {

	private static Logger log = LoggerFactory.getLogger(KeepAliveMessage.class);
	
	@Autowired
	private ServiceAgent serviceAgent;
	
	@Autowired
	private ServerConfig serverConfig;
	
	@Autowired
	@Qualifier("eventTaskProducer")
	private Producer<MessageTask> taskProducer;
	
	@Autowired
	private ServiceTask serviceTask;
	
	@Override
	public void process(MessageAgent message) {
		log.debug("Message Receive {}",message.toString());
		if (!"agent-0".equalsIgnoreCase(message.getAgent())){
			Agent agent = new Agent.BuilderAgent().nameAgent(message.getAgent()).port(message.getPort()).server(getServer()).build();
			serviceAgent.save(agent);
			includeTasksProcessing();
		}
	}
	
	private Server getServer() {
		Server serverInfo = new Server();	
		serverInfo.setName(serverConfig.getServer().getName());
		serverInfo.setHost(serverConfig.getServer().getHost());
		return serverInfo;
	}
	
	private void includeTasksProcessing(){
		List<Task> tasks = serviceTask.getTaskByStatus(StatusProcessTaskEnum.PROCESSING.getName());
		tasks.stream().forEach(task -> {
			String queueName = String.format("%s.%s.IN", task.getBot().getNameBot(), task.getId());
			MessageTask message = new MessageTask(queueName, task.getBot().getNameBot());
			taskProducer.send("", message);
			log.info("Notify queue {} with tasks Processing", queueName);
		});		
	}

}
