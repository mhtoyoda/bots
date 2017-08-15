package com.fiveware.config.agent;

import com.fiveware.loader.ClassLoaderConfig;
import com.fiveware.messaging.BrokerManager;
import com.fiveware.messaging.Producer;
import com.fiveware.messaging.QueueName;
import com.fiveware.messaging.TypeMessage;
import com.fiveware.model.Agent;
import com.fiveware.model.Bot;
import com.fiveware.model.BotClassLoaderContext;
import com.fiveware.model.message.MessageAgent;
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
	private AgentListener agentListener;
	
	@Autowired
	private BrokerManager brokerManager;
	
    @Autowired
    @Qualifier("eventMessageProducer")
    private Producer<MessageAgent> producer;
	
	public void initAgent() {
		saveAgentServerBots();
		bindQueueAgenteInTaskTopic("topic-exchange",
				Lists.newArrayList(data.getAgentName()));
		notifyServerUpAgent();
	}

	private Agent saveAgentServerBots() {
		data.setAgentName("Agent-"+agentListener.getAgentPort());
		Agent agent = new Agent.BuilderAgent()
				.nameAgent(data.getAgentName())
				.ip(data.getIp())
				.port(agentListener.getAgentPort())
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

	private void bindQueueAgenteInTaskTopic(String exchangeName, List<String> agent){
		brokerManager.createTopicExchange(exchangeName, agent);
	}
	
	private void notifyServerUpAgent(){
		MessageAgent message = new MessageAgent(data.getHost(), "Agent-"+agentListener.getAgentPort(),
				data.getIp(), agentListener.getAgentPort(),
				TypeMessage.START_AGENT, "Start Agent!");
        producer.send(QueueName.EVENTS.name(), message);
	}
}
