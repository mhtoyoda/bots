package com.fiveware.service.discovery;

import com.fiveware.exception.AgentBotNotFoundException;
import com.fiveware.model.entities.Agent;
import com.fiveware.service.ServiceServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AgentServiceDiscovery implements ServiceDiscovery {
	
	@Autowired
	private ServiceServer serviceServer;

	
	@Override
	public String getUrlService(String serverName, String nameBot, String endpoint) throws AgentBotNotFoundException {
		Optional<List<Agent>> agents = serviceServer.getAllAgentsByBotName(serverName, nameBot, endpoint);
		Integer size = agents.map(List::size).orElse(0);
		if(size > 0){
			Agent agent = getAgent(agents.get());
			String pattern = "http://%s:%d/api/%s/%s";			
			return String.format(pattern, agent.getIp(), agent.getPort(), nameBot, endpoint);
		}
		throw new AgentBotNotFoundException(String.format("Agente com Bot %s não localizado", nameBot));		
	}
	
	private Agent getAgent(List<Agent> agents){
		//TODO agents verificar qual esta ocioso
		return agents.get(0);
	}
}
