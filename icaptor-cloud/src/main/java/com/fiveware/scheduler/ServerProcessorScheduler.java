package com.fiveware.scheduler;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fiveware.config.ServerConfig;
import com.fiveware.exception.AuthenticationBotException;
import com.fiveware.exception.RecoverableException;
import com.fiveware.exception.RuntimeBotException;
import com.fiveware.messaging.Producer;
import com.fiveware.messaging.Receiver;
import com.fiveware.model.Agent;
import com.fiveware.model.AgentParameter;
import com.fiveware.model.Bot;
import com.fiveware.model.Parameter;
import com.fiveware.model.StatusProcessItemTaskEnum;
import com.fiveware.model.StatusProcessTaskEnum;
import com.fiveware.model.Task;
import com.fiveware.model.message.MessageBot;
import com.fiveware.parameter.ParameterInfo;
import com.fiveware.parameter.ParameterResolver;
import com.fiveware.pulling.BrokerPulling;
import com.fiveware.service.ServiceAgent;
import com.fiveware.service.ServiceServer;
import com.fiveware.task.TaskManager;

@Component
public class ServerProcessorScheduler extends BrokerPulling<MessageBot>{

	private static Logger log = LoggerFactory.getLogger(ServerProcessorScheduler.class);

	@Autowired
	@Qualifier("eventBotReceiver")
	private Receiver<MessageBot> receiver;

	@Autowired
	private ServiceServer serviceServer;

	@Autowired
	private ServiceAgent serviceAgent;

	@Autowired
	private ServerConfig serverConfig;

	@Autowired
	private TaskManager taskManager;

	@Autowired
	private Producer<MessageBot> producer;
	
	@Autowired
	private ParameterResolver parameterResolver;


	@Scheduled(fixedDelayString = "${icaptor.broker.queue-send-schedular-time}")
	public void process() {

		List<Agent> agents = serviceServer.getAllAgent(serverConfig.getServer().getName());
		agents.forEach(agent -> {
			log.info("Pulling Error [Agent]: {}", agent.getNameAgent());
			List<Bot> bots = serviceAgent.findBotsByAgent(agent.getNameAgent());
			bots.forEach(bot -> {
				String botName = bot.getNameBot();
				String nameQueue = "task.out";
				pullMessage(botName, nameQueue);
			});
		});
	}

	/**
	 * Validar regras de bloqueio para pulling de fila
	 *
	 */
	@Override
	public boolean canPullingMessage() {
		return true;
	}

	/**
	 * Processa mensagem recebida do Broker
	 */
	@Override
	public void processMessage(String botName, MessageBot messageBot) throws RuntimeBotException {
		log.debug("Linha resultado: {}", messageBot.getLineResult());

		if (!Objects.isNull(messageBot.getException()) &&
				messageBot.getException() instanceof RecoverableException ){
			int parameterRetry = getParameter(botName, "retry");

			verifyRetry(botName, messageBot, parameterRetry);
		}
		
		if (!Objects.isNull(messageBot.getException()) &&
				messageBot.getException() instanceof AuthenticationBotException ){
			int parameterRetry = getParameter(botName, "retry");

			verifyRetry(botName, messageBot, parameterRetry);
			invalidateParameterCredential(botName, messageBot);
		}
		
		if (!Objects.isNull(messageBot.getStatusProcessItemTaskEnum())){
			taskManager.updateItemTask(messageBot);			
		}
	}

	private void invalidateParameterCredential(String botName, MessageBot messageBot) {
		if(parameterResolver.hasNecessaryParameterFromBot(botName)){
			AgentParameter agentParameter = parameterResolver.findAgentParameterByNameAgent(messageBot.getNameAgent());
			if(Objects.nonNull(agentParameter)){
				parameterResolver.removeAgentParameter(agentParameter);
				parameterResolver.disableParameter(agentParameter.getParameter().getId());
			}
		}
	}

	private void verifyRetry(String botName, MessageBot messageBot, int parameterRetry) {
		Task task = taskManager.getTask(messageBot.getTaskId());
		String queueName = String.format("%s.%s.IN", botName, task.getId());

		int attemptsCount = messageBot.getAttemptsCount() + 1;
		messageBot.setAttemptsCount(attemptsCount);

		if(messageBot.getAttemptsCount() >= parameterRetry){
			messageBot.setAttemptsCount(attemptsCount-1);
			messageBot.setStatusProcessItemTaskEnum(StatusProcessItemTaskEnum.ERROR);
			taskManager.updateItemTask(messageBot);
			taskManager.updateTask(messageBot.getTaskId(), StatusProcessTaskEnum.ERROR);
			return;
		}
		producer.send(queueName, messageBot);
	}

	@Override
	public Optional<MessageBot> receiveMessage(String queueName) {
		return Optional.ofNullable(receiver.receive(queueName));
	}

	private int getParameter(String botName, String typeParameter){
		ParameterInfo parameterByBot = parameterResolver.getParameterByBot(botName);
		Optional<ParameterInfo> optional = Optional.ofNullable(parameterByBot);
		if(optional.isPresent()){

			Parameter parameter = optional.get().getParameters().get(typeParameter);

			if(Objects.isNull(parameter))
				getParameterCloud(typeParameter);

			return getValue(parameter);
		}
		return getParameterCloud(typeParameter);
	}

	private int getValue(Parameter parameter) {
		if(parameter.getFieldValue().contains(":")){
			String[] valuesField = parameter.getFieldValue().split(":");
			return Integer.parseInt(valuesField[1]);
		}
		return Integer.parseInt(parameter.getFieldValue());
	}
	
	private int getParameterCloud(String typeParameter){
		Parameter parameterCloud = parameterResolver.getParameterCloud(typeParameter);
		return getValue(parameterCloud);
	}
}