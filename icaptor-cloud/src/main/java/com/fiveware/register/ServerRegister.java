package com.fiveware.register;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fiveware.config.ServerConfig;
import com.fiveware.config.ServerInfo;
import com.fiveware.messaging.BrokerManager;
import com.fiveware.model.Agent;
import com.fiveware.model.Server;
import com.fiveware.service.ServiceServer;

@Component
public class ServerRegister {

	private static final Logger log = LoggerFactory.getLogger(ServerRegister.class);

	@Autowired
	private ServerConfig serverConfig;

	@Autowired
	private ServiceServer serviceServer;

	@Autowired
	private BrokerManager brokerManager;
	
	@PostConstruct
	public void register() {
		boolean serverOff = true;
		while(serverOff){
			try{
				Thread.sleep(10000);
				ServerInfo server = serverConfig.getServer();
				log.info("Init Server Host: {} - Port: {}", server.getHost(), server.getPort());
				registerServer();
				createQueueTaskOut();
				createTopicAgents();
				serverOff = false;
			}catch (Exception e) {		
				log.error("Error while Init Server: {}", e.getMessage());
			}			
		}
	}

	private void registerServer() {
		ServerInfo serverInfo = serverConfig.getServer();
		Server server = new Server();
		server.setName(serverInfo.getName());
		String host = serverInfo.getHost() + ":" + serverInfo.getPort();
		server.setHost(host);
		serviceServer.save(server);
		log.info("Register Server Host: {}", host);
	}
	
	private void createQueueTaskOut(){
		brokerManager.createQueue("task.out");		
	}
	
	private void createTopicAgents(){
	   	String exchangeName = "topic-exchange";
    	List<Agent> allAgent = serviceServer.getAllAgent(serverConfig.getServer().getName());
    	List<String> agents = allAgent.stream().map(Agent::getNameAgent).collect(Collectors.toList());
		brokerManager.createTopicExchange(exchangeName, agents);
	}
}
