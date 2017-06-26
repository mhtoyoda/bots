package com.fiveware.config.agent;

import java.util.List;
import java.util.Optional;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fiveware.dao.AgentDAO;
import com.fiveware.dao.BotDAO;
import com.fiveware.dao.ServerDAO;
import com.fiveware.loader.ClassLoaderConfig;
import com.fiveware.messaging.QueueCreator;
import com.fiveware.model.Agent;
import com.fiveware.model.Bot;
import com.fiveware.model.BotClassLoaderContext;
import com.fiveware.model.Server;
import com.google.common.collect.Lists;

@Component
public class AgentConfig {

	@Autowired
	private AgentConfigProperties data;

	@Autowired
	@Qualifier("mapClassLoaderConfig")
	private ClassLoaderConfig classLoaderConfig;

	@Autowired
	private AgentDAO agentDAO;

	@Autowired
	private BotDAO botDAO;

	@Autowired
	private ServerDAO serverDAO;

	@Autowired
	private QueueCreator queueCreator;

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
		serverDAO.save(server);
		agentDAO.save(agent);
		createQueueBots(botList);
	}
	
	private void createQueueBots(List<Bot> bots){
		bots.forEach(bot -> {
			queueCreator.createQueue(bot.getNameBot());
		});
	}

	private Bot getBot(BotClassLoaderContext botClassLoaderContext) {
		Optional<Bot> optional = botDAO.findByNameBot(botClassLoaderContext.getNameBot());
		if (optional.isPresent()) {
			return optional.get();
		}

		Bot bot = new Bot();
		bot.setEndpoint(botClassLoaderContext.getEndpoint());
		bot.setNameBot(botClassLoaderContext.getNameBot());
		bot.setMethod(botClassLoaderContext.getMethod());
		return botDAO.save(bot);
	}

	private Agent getAgent(Server server) {
		Optional<Agent> optional = agentDAO.findByNameAgent(data.getAgentName());
		if (optional.isPresent()) {
			return optional.get();
		}
		Agent agent = new Agent();
		agent.setIp(data.getIp());
		agent.setNameAgent(data.getAgentName());
		agent.setPort(0);
		agent.setServer(server);
		return agentDAO.save(agent);
	}

	private Server getServer() {
		Optional<Server> optional = serverDAO.findByName(data.getServer());
		if (optional.isPresent()) {
			return optional.get();
		}
		Server serverInfo = new Server();
		serverInfo.setName(data.getServer());
		serverInfo.setHost(data.getHost());
		return serverDAO.save(serverInfo);
	}
}