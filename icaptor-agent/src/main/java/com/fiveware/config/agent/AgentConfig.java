package com.fiveware.config.agent;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fiveware.helpers.NetIdentity;
import com.fiveware.loader.ClassLoaderConfig;
import com.fiveware.messaging.BrokerManager;
import com.fiveware.messaging.Producer;
import com.fiveware.messaging.QueueName;
import com.fiveware.messaging.TypeMessage;
import com.fiveware.model.BotClassLoaderContext;
import com.fiveware.model.BotReturnTypeFormatter;
import com.fiveware.model.IcaptorPameterContext;
import com.fiveware.model.message.MessageAgent;
import com.fiveware.model.message.MessageAgentBot;
import com.fiveware.model.message.MessageAgentBotFormatter;
import com.fiveware.model.message.MessageParameterAgentBot;
import com.google.common.collect.Lists;
@Component
public class AgentConfig {


	static Logger logger = LoggerFactory.getLogger(AgentConfig.class);

	@Autowired
	private AgentConfigProperties data;

	@Autowired
	@Qualifier("mapClassLoaderConfig")
	private ClassLoaderConfig classLoaderConfig;

	@Autowired
	private AgentListener agentListener;

	@Autowired
	private BrokerManager brokerManager;

    @Autowired
    @Qualifier("eventMessageProducer")
    private Producer<MessageAgent> producer;

	public void initAgent() {
		if(agentListener.getAgentPort() != 0){
			data.setAgentName("Agent-"+agentListener.getAgentPort());
			bindQueueAgenteInTaskTopic("topic-exchange",
					Lists.newArrayList(data.getAgentName()));
			createQueueParameter();
			notifyServerUpAgent();
		}
	}

	private MessageAgent addBots(MessageAgent message) {
		Optional.ofNullable(classLoaderConfig.getAll())
				.ifPresent(new Consumer<List<BotClassLoaderContext>>() {
			@Override
			public void accept(List<BotClassLoaderContext> bots) {
				bots.forEach(botClassLoader -> {
					MessageAgentBot bot = new MessageAgentBot();
					bot.setEndpoint(botClassLoader.getEndpoint());
					bot.setNameBot(botClassLoader.getNameBot());
					bot.setNameMethod(botClassLoader.getMethod());
					bot.setFieldsInput(StringUtils.join(botClassLoader.getInputDictionary().getFields(), ","));
					bot.setFieldsOutput(StringUtils.join(botClassLoader.getOutputDictionary().getFields(), ","));
					bot.setSeparatorFile(botClassLoader.getInputDictionary().getSeparator());
					bot.setTypeFileIn(botClassLoader.getInputDictionary().getTypeFileIn());
					bot.setVersion(botClassLoader.getVersion());
					bot.setDescription(botClassLoader.getDescription());
					bot.setClassloader(botClassLoader.getClassLoader());
					bot = saveParametersBot(botClassLoader.getParameterContexts(), bot);
					bot = saveBotFormatter(botClassLoader.getBotReturnTypeFormatters(), bot);
					message.addMessageAgentBots(bot);
				});
			}
		});
		return message;
	}

	private void bindQueueAgenteInTaskTopic(String exchangeName, List<String> agent){
		brokerManager.createTopicExchange(exchangeName, agent);
	}

	private void createQueueParameter(){
		brokerManager.createQueue("parameter."+data.getAgentName());
	}

	private void notifyServerUpAgent(){
		MessageAgent message = new MessageAgent(data.getHost(), "Agent-"+agentListener.getAgentPort(),
				data.getIp(), agentListener.getAgentPort(),
				TypeMessage.START_AGENT, "Start Agent!");

        message.setAddressAgent(NetIdentity.ipAddress());

		message = addBots(message);
        producer.send(QueueName.EVENTS.name(), message);
	}

	private MessageAgentBot saveParametersBot(List<IcaptorPameterContext> parameters, MessageAgentBot bot){
		if(CollectionUtils.isNotEmpty(parameters)){
			parameters.forEach(parameter -> {
				MessageParameterAgentBot messageParameterAgentBot = new MessageParameterAgentBot();
				messageParameterAgentBot.setParameterValue(parameter.getValue());
				messageParameterAgentBot.setTypeParameterCredential(parameter.isCredential());
				messageParameterAgentBot.setTypeParameterExclusive(parameter.isExclusive());
				messageParameterAgentBot.setTypeParameterName(parameter.getNameTypeParameter());
				bot.addParameters(messageParameterAgentBot);
			});
		}
		return bot;
	}
	
	private MessageAgentBot saveBotFormatter(List<BotReturnTypeFormatter> formatters, MessageAgentBot bot){
		if(CollectionUtils.isNotEmpty(formatters)){
			formatters.forEach(formatter -> {
				MessageAgentBotFormatter messageAgentBotFormatter = new MessageAgentBotFormatter();
				messageAgentBotFormatter.setFieldIndex(formatter.getFieldIndex());
				messageAgentBotFormatter.setNameField(formatter.getNameField());
				messageAgentBotFormatter.setTypeFile(formatter.getTypeFile());				
				bot.addBotFormatter(messageAgentBotFormatter);
			});
		}
		return bot;
	}

}