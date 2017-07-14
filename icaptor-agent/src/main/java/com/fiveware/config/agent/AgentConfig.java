package com.fiveware.config.agent;

import com.fiveware.loader.ClassLoaderConfig;
import com.fiveware.messaging.QueueCreator;
import com.fiveware.model.Agent;
import com.fiveware.model.Bot;
import com.fiveware.model.BotClassLoaderContext;
import com.fiveware.model.Server;
import com.fiveware.service.ServiceAgent;
import com.fiveware.service.ServiceBot;
import com.fiveware.service.ServiceServer;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AgentConfig {

	@Autowired
	private AgentConfigProperties data;

	@Autowired
	@Qualifier("mapClassLoaderConfig")
	private ClassLoaderConfig classLoaderConfig;

	@Autowired
	private ServiceAgent serviceAgent;

	@Autowired
	private ServiceBot serviceBot;

	@Autowired
	private ServiceServer serviceServer;

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
		serviceServer.save(server);
		serviceAgent.save(agent);
		createQueueBots(botList);
	}
	
	private void createQueueBots(List<Bot> bots){
		bots.forEach(bot -> {
			queueCreator.createQueue(bot.getNameBot());
		});
	}

	private Bot getBot(BotClassLoaderContext botClassLoaderContext) {
		Optional<Bot> optional = serviceBot.findByNameBot(botClassLoaderContext.getNameBot());
		if (optional.isPresent()) {
			return optional.get();
		}

		Bot bot = new Bot();
		bot.setEndpoint(botClassLoaderContext.getEndpoint());
		bot.setNameBot(botClassLoaderContext.getNameBot());
		bot.setMethod(botClassLoaderContext.getMethod());
		return serviceBot.save(bot);
	}

	private Agent getAgent(Server server) {
		Optional<Agent> optional = serviceAgent.findByNameAgent(data.getAgentName());
		if (optional.isPresent()) {
			Agent agent = serviceAgent.findOne(optional.get().getId());
			agent.setPort(agentListener.getAgentPort());
			agent = serviceAgent.save(agent);
			return agent;
		}

		Agent agent = new Agent.BuilderAgent()
				.nameAgent(data.getAgentName())
				.ip(data.getIp())
				.port(agentListener.getAgentPort())
				.server(server)
				.build();


		return serviceAgent.save(agent);
	}

	private Server getServer() {
		Optional<Server> optional = serviceServer.findByName(data.getServer());
		if (optional.isPresent()) {
			return optional.get();
		}
		Server serverInfo = new Server();
		serverInfo.setName(data.getServer());
		serverInfo.setHost(data.getHost());
		return serviceServer.save(serverInfo);
	}
}
