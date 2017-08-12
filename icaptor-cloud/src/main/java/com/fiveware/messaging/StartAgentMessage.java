package com.fiveware.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fiveware.config.ServerConfig;
import com.fiveware.model.Agent;
import com.fiveware.model.Server;
import com.fiveware.model.message.MessageAgent;
import com.fiveware.service.ServiceAgent;

@Component("START_AGENT")
public class StartAgentMessage implements ConsumerTypeMessage<MessageAgent> {

	private static Logger log = LoggerFactory.getLogger(StartAgentMessage.class);
	
	@Autowired
	private ServiceAgent serviceAgent;
	
	@Autowired
	private ServerConfig serverConfig;
	
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
}
