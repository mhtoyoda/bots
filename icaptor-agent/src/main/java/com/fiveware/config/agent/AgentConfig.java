package com.fiveware.config.agent;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fiveware.loader.ClassLoaderConfig;
import com.fiveware.messaging.BrokerManager;
import com.fiveware.messaging.Producer;
import com.fiveware.messaging.QueueName;
import com.fiveware.messaging.TypeMessage;
import com.fiveware.model.Agent;
import com.fiveware.model.Bot;
import com.fiveware.model.BotClassLoaderContext;
import com.fiveware.model.IcaptorPameterContext;
import com.fiveware.model.Parameter;
import com.fiveware.model.TypeParameter;
import com.fiveware.model.message.MessageAgent;
import com.fiveware.parameter.ScopeParameterEnum;
import com.fiveware.service.ServiceAgent;
import com.fiveware.service.ServiceBot;
import com.fiveware.service.ServiceParameter;
import com.google.common.collect.Lists;

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
	
	@Autowired
	private ServiceBot serviceBot;
	
	@Autowired
	private ServiceParameter serviceParameter;
	
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
					Bot bot = findBotByName(botClassLoader.getNameBot());
					bot.setEndpoint(botClassLoader.getEndpoint());
					bot.setNameBot(botClassLoader.getNameBot());
					bot.setMethod(botClassLoader.getMethod());
					bot.setFieldsInput(StringUtils.join(botClassLoader.getInputDictionary().getFields(), ","));
					bot.setFieldsOutput(StringUtils.join(botClassLoader.getOutputDictionary().getFields(), ","));
					bot.setSeparatorFile(botClassLoader.getInputDictionary().getSeparator());
					bot.setTypeFileIn(botClassLoader.getInputDictionary().getTypeFileIn());
					if(bot.getId() == null){
						bot = serviceBot.save(bot);
					}
					saveParametersBot(botClassLoader.getPameterContexts(), bot);
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
	
	private Bot findBotByName(String nameBot){
		Optional<Bot> bot = Optional.empty();
		try{
			bot = serviceBot.findByNameBot(nameBot);
			return bot.get();
		}catch (Exception e) {			
			return bot.orElse(new Bot());
		}
	}
	
	private void saveParametersBot(List<IcaptorPameterContext> parameters, Bot bot){
		List<Parameter> parameterList = serviceParameter.getParameterByBot(bot.getNameBot());
		if(CollectionUtils.isNotEmpty(parameterList)){
			serviceParameter.delete(parameterList);
		}
		if(CollectionUtils.isNotEmpty(parameters)){
			parameters.forEach(parameter -> {
				TypeParameter typeParameter = serviceParameter.getTypeParameterByName(parameter.getNameTypeParameter());
				if(null == typeParameter){
					typeParameter = new TypeParameter();
					typeParameter.setName(parameter.getNameTypeParameter());
					typeParameter.setExclusive(parameter.isExclusive());
					typeParameter.setCredential(parameter.isCredential());
					typeParameter = serviceParameter.saveTypeParameter(typeParameter);				
				}
				Parameter param = new Parameter();
				param.setBot(bot);
				param.setActive(true);
				param.setFieldValue(parameter.getName()+":"+parameter.getValue());
				param.setScopeParameter(serviceParameter.getScopeParameterById(ScopeParameterEnum.BOT.getId()));
				param.setTypeParameter(typeParameter);
				serviceParameter.save(param);
			});
		}
	}
}