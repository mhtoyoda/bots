package com.fiveware.config.agent;

import java.util.List;
import java.util.Optional;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fiveware.loader.ClassLoaderConfig;
import com.fiveware.messaging.QueueCreator;
import com.fiveware.model.entities.Agent;
import com.fiveware.model.entities.Bot;
import com.fiveware.model.BotClassLoaderContext;
import com.fiveware.model.entities.Server;
import com.fiveware.repository.AgentRepository;
import com.fiveware.repository.BotRepository;
import com.fiveware.repository.ServerRepository;
import com.google.common.collect.Lists;

@Component
public class AgentConfig {

	@Autowired
	private AgentConfigProperties data;

	@Autowired
	@Qualifier("mapClassLoaderConfig")
	private ClassLoaderConfig classLoaderConfig;

	@Autowired
	private AgentRepository agentRepository;

	@Autowired
	private BotRepository botRepository;

	@Autowired
	private ServerRepository serverRepository;

	@Autowired
	private QueueCreator queueCreator;
	
	@Autowired
	private AgentListener agentListener;
	
	public void saveAgentBot() {
		List<Bot> botList = Lists.newArrayList();
		List<BotClassLoaderContext> bots = classLoaderConfig.getAll();
		Server server = getServer();
		Agent agent = getAgent(server);
		if (CollectionUtils.isNotEmpty(bots)) {
			bots.forEach(bot -> {
				Bot botInfo = getBot(bot);				
				botList.add(botInfo);
			});
		}
		agent.setBots(botList);
		server.setAgents(Lists.newArrayList(agent));
		serverRepository.save(server);
		agentRepository.save(agent);
		createQueueBots(botList);
	}
	
	private void createQueueBots(List<Bot> bots){
		bots.forEach(bot -> {
			queueCreator.createQueue(bot.getNameBot());
		});
	}

	private Bot getBot(BotClassLoaderContext botClassLoaderContext) {
		Optional<Bot> optional = botRepository.findByNameBot(botClassLoaderContext.getNameBot());
		if (optional.isPresent()) {
			return optional.get();
		}

		Bot bot = new Bot();
		bot.setEndpoint(botClassLoaderContext.getEndpoint());
		bot.setNameBot(botClassLoaderContext.getNameBot());
		bot.setMethod(botClassLoaderContext.getMethod());
		return botRepository.save(bot);
	}

	private Agent getAgent(Server server) {
		Optional<Agent> optional = agentRepository.findByNameAgent(data.getAgentName());
		if (optional.isPresent()) {
			Agent agent = agentRepository.findOne(optional.get().getId());
			agent.setPort(agentListener.getAgentPort());
			agent = agentRepository.save(agent);
			return agent;
		}
		Agent agent = new Agent();
		agent.setIp(data.getIp());
		agent.setNameAgent(data.getAgentName());
		agent.setPort(agentListener.getAgentPort());
		agent.setServer(server);
		return agentRepository.save(agent);
	}

	private Server getServer() {
		Optional<Server> optional = serverRepository.findByName(data.getServer());
		if (optional.isPresent()) {
			return optional.get();
		}
		Server serverInfo = new Server();
		serverInfo.setName(data.getServer());
		serverInfo.setHost(data.getHost());
		return serverRepository.save(serverInfo);
	}
}
