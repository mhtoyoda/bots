package com.fiveware.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fiveware.config.ServerConfig;
import com.fiveware.model.Agent;
import com.fiveware.model.Server;
import com.fiveware.model.message.MessageAgent;
import com.fiveware.model.message.MessageTask;
import com.fiveware.service.ServiceAgent;
import com.fiveware.service.ServiceTask;

@Component("START_AGENT")
public class StartAgentMessage implements ConsumerTypeMessage<MessageAgent> {

	private static Logger log = LoggerFactory.getLogger(StartAgentMessage.class);
	
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
		log.info("Start Agent {}",message.toString());
		Agent agent = new Agent.BuilderAgent().nameAgent(message.getAgent()).port(message.getPort()).server(getServer()).build();
		serviceAgent.save(agent);
	}

	private Server getServer() {
		Server serverInfo = new Server();	
		serverInfo.setName(serverConfig.getServer().getName());
		serverInfo.setHost(serverConfig.getServer().getHost());
		return serverInfo;
	}
	
//	private void includeTasksProcessing(){
//		serviceTask.getTaskByStatus()
//		String queueName = String.format("%s.%s.IN", task.getBot().getNameBot(), task.getId());
//		
//	}
}
