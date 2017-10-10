package com.fiveware.messaging;

import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fiveware.config.ServerConfig;
import com.fiveware.model.Agent;
import com.fiveware.model.Bot;
import com.fiveware.model.Parameter;
import com.fiveware.model.Server;
import com.fiveware.model.StatusProcessTaskEnum;
import com.fiveware.model.Task;
import com.fiveware.model.TypeParameter;
import com.fiveware.model.message.MessageAgent;
import com.fiveware.model.message.MessageParameterAgentBot;
import com.fiveware.model.message.MessageTask;
import com.fiveware.parameter.ScopeParameterEnum;
import com.fiveware.service.ServiceAgent;
import com.fiveware.service.ServiceBot;
import com.fiveware.service.ServiceParameter;
import com.fiveware.service.ServiceTask;
import com.google.common.collect.Lists;

@Component("START_AGENT")
public class StartAgentMessage implements ConsumerTypeMessage<MessageAgent> {

	private static Logger log = LoggerFactory.getLogger(StartAgentMessage.class);

	@Autowired
	private ServiceAgent serviceAgent;

	@Autowired
	private ServerConfig serverConfig;

	@Autowired
	@Qualifier("eventTaskProducer")
	private Producer<MessageTask> taskProducer;

	@Autowired
	private ServiceTask serviceTask;

	@Autowired
	private ServiceBot serviceBot;
	
	@Autowired
	private ServiceParameter serviceParameter;

	@Override
	public void process(MessageAgent message) {
		log.info("Start Agent {}", message.toString());
		saveAgentServerBots(message);
		includeTasksProcessing();
	}

	private Server getServer() {
		Server serverInfo = new Server();
		serverInfo.setName(serverConfig.getServer().getName());
		serverInfo.setHost(serverConfig.getServer().getHost());
		return serverInfo;
	}

	private void includeTasksProcessing() {
		List<Task> tasks = serviceTask.getTaskByStatus(StatusProcessTaskEnum.PROCESSING.getName());
		tasks.stream().forEach(task -> {
			String queueName = String.format("%s.%s.IN", task.getBot().getNameBot(), task.getId());
			MessageTask message = new MessageTask(queueName, task.getBot().getNameBot());
			taskProducer.send("", message);
			log.info("Notify queue {} with tasks Processing", queueName);
		});
	}

	private void saveAgentServerBots(MessageAgent message) {
		Agent agent = serviceAgent.findByNameAgent(message.getAgent());
		if (null == agent) {
			agent = new Agent.BuilderAgent().nameAgent(message.getAgent()).ip(message.getIp()).port(message.getPort()).server(getServer()).build();
			agent = serviceAgent.save(agent);
		}
		addBots(message, agent);
	}

	private void addBots(MessageAgent message, Agent agent) {
		if (CollectionUtils.isNotEmpty(message.getMessageAgentBots())) {
			final List<Bot> botList = Lists.newArrayList();
			message.getMessageAgentBots().forEach(botMessage -> {
				Bot bot = findBotByName(botMessage.getNameBot());
				bot.setEndpoint(botMessage.getEndpoint());
				bot.setNameBot(botMessage.getNameBot());
				bot.setMethod(botMessage.getNameMethod());
				bot.setFieldsInput(botMessage.getFieldsInput());
				bot.setFieldsOutput(botMessage.getFieldsOutput());
				bot.setSeparatorFile(botMessage.getSeparatorFile());
				bot.setTypeFileIn(botMessage.getTypeFileIn());
				bot.setClassloader(botMessage.getClassloader());
				bot.setDescription(botMessage.getDescription());
				bot.setVersion(botMessage.getVersion());
				if (bot.getId() == null) {
					bot = serviceBot.save(bot);
				}

				saveParametersBot(botMessage.getParameters(), bot);
				botList.add(bot);
			});
			agent.setBots(botList);
			agent = serviceAgent.save(agent);
		}
	}

	private void saveParametersBot(List<MessageParameterAgentBot> parameters, Bot bot) {
		List<Parameter> parameterList = serviceParameter.getParameterByBot(bot.getNameBot());
		if (CollectionUtils.isNotEmpty(parameterList)) {
			serviceParameter.delete(parameterList);
		}
		if (CollectionUtils.isNotEmpty(parameters)) {
			parameters.forEach(parameter -> {
				TypeParameter typeParameter = serviceParameter.getTypeParameterByName(parameter.getTypeParameterName());
				if (null == typeParameter) {
					typeParameter = new TypeParameter();
					typeParameter.setName(parameter.getTypeParameterName());
					typeParameter.setExclusive(parameter.getTypeParameterExclusive());
					typeParameter.setCredential(parameter.getTypeParameterExclusive());
					typeParameter = serviceParameter.saveTypeParameter(typeParameter);
				}
				Parameter param = new Parameter();
				param.setBot(bot);
				param.setActive(true);
				param.setFieldValue(parameter.getParameterValue());
				param.setScopeParameter(serviceParameter.getScopeParameterById(ScopeParameterEnum.BOT.getId()));
				param.setTypeParameter(typeParameter);
				serviceParameter.save(param);
			});
		}
	}

	private Bot findBotByName(String nameBot) {
		Optional<Bot> bot = Optional.empty();
		try {
			bot = serviceBot.findByNameBot(nameBot);
			return bot.get();
		} catch (Exception e) {
			return bot.orElse(new Bot());
		}
	}
}