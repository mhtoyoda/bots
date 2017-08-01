package com.fiveware.config.agent;

import com.fiveware.loader.ClassLoaderConfig;
import com.fiveware.messaging.QueueCreator;
import com.fiveware.model.Agent;
import com.fiveware.model.Bot;
import com.fiveware.model.BotClassLoaderContext;
import com.fiveware.model.Server;
import com.fiveware.service.ServiceAgent;
import com.google.common.collect.Lists;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

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
	private QueueCreator queueCreator;
	
	@Autowired
	private AgentListener agentListener;
	
	public void saveAgentAndCreateQueue() {
		saveAgentServerBots()
				.getBots()
				.forEach(
					(bot)->queueCreator.createQueue(bot.getNameBot())
				);
	}

	private Agent saveAgentServerBots() {
		Agent agent = new Agent.BuilderAgent()
				.nameAgent(data.getAgentName())
				.ip(data.getIp())
				.port(agentListener.getAgentPort())
				.server(getServer())
				.bots(getBots())
				.build();

		return serviceAgent.save(agent);
	}

	private List<Bot> getBots() {
		final List<Bot> botList = Lists.newArrayList();

		Optional.ofNullable(classLoaderConfig.getAll())
				.ifPresent(new Consumer<List<BotClassLoaderContext>>() {
			@Override
			public void accept(List<BotClassLoaderContext> bots) {
				bots.forEach(botClassLoader -> {
					Bot bot = new Bot();
					bot.setEndpoint(botClassLoader.getEndpoint());
					bot.setNameBot(botClassLoader.getNameBot());
					bot.setMethod(botClassLoader.getMethod());
					bot.setFieldsInput(StringUtils.join(botClassLoader.getInputDictionary().getFields(), ","));
					bot.setFieldsOutput(StringUtils.join(botClassLoader.getOutputDictionary().getFields(), ","));
					bot.setSeparatorFile(botClassLoader.getInputDictionary().getSeparator());
					bot.setTypeFileIn(botClassLoader.getInputDictionary().getTypeFileIn());
					botList.add(bot);
				});
			}
		});
		return botList;
	}

	private Server getServer() {
		Server serverInfo = new Server();
		serverInfo.setName(data.getServer());
		serverInfo.setHost(data.getHost());
		return serverInfo;
	}
}
