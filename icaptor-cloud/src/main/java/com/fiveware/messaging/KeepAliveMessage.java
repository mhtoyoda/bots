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

@Component("KEEP_ALIVE")
public class KeepAliveMessage implements ConsumerTypeMessage<MessageAgent> {

	private static Logger log = LoggerFactory.getLogger(KeepAliveMessage.class);
	
	@Autowired
	private ServiceAgent serviceAgent;
	
	@Autowired
	private ServerConfig serverConfig;
	
	@Override
	public void process(MessageAgent message) {
		log.debug("Message Receive {}",message.toString());
		if (!"agent-0".equalsIgnoreCase(message.getAgent())){
			Agent agent = new Agent.BuilderAgent().nameAgent(message.getAgent()).port(message.getPort()).server(getServer()).build();
			serviceAgent.save(agent);
		}
	}
	
	private Server getServer() {
		Server serverInfo = new Server();	
		serverInfo.setName(serverConfig.getServer().getName());
		serverInfo.setHost(serverConfig.getServer().getHost());
		return serverInfo;
	}

}