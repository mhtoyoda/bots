package com.fiveware.scheduler;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.fiveware.exception.RecoverableException;
import com.fiveware.exception.RuntimeBotException;
import com.fiveware.messaging.Producer;
import com.fiveware.model.Task;
import com.fiveware.task.StatusProcessItemTaskEnum;
import com.fiveware.task.StatusProcessTaskEnum;
import com.fiveware.task.TaskManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fiveware.config.ServerConfig;
import com.fiveware.messaging.Receiver;
import com.fiveware.model.Agent;
import com.fiveware.model.Bot;
import com.fiveware.model.message.MessageBot;
import com.fiveware.pulling.BrokerPulling;
import com.fiveware.service.ServiceAgent;
import com.fiveware.service.ServiceServer;
import com.fiveware.util.FileUtil;

@Component
public class ServerProcessorScheduler extends BrokerPulling<MessageBot>{

	private static Logger log = LoggerFactory.getLogger(ServerProcessorScheduler.class);

	@Value("${retries}")
	private int retries;

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
	private FileUtil fileUtil;

	@Autowired
	private TaskManager taskManager;

	@Autowired
	private Producer<MessageBot> producer;

	@Scheduled(fixedDelayString = "${broker.queue.send.schedularTime}")
	public void process() {
		List<Agent> agents = serviceServer.getAllAgent(serverConfig.getServer().getName());
		agents.forEach(agent -> {
			log.info("Pulling Message [Agent]: {}", agent.getNameAgent());
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

			Task task = taskManager.getTask(messageBot.getTaskId());
			String queueName = String.format("%s.%s.IN", botName, task.getId());

			int attemptsCount = messageBot.getAttemptsCount() + 1;
			messageBot.setAttemptsCount(attemptsCount);

			if(messageBot.getAttemptsCount() > retries){
				messageBot.setAttemptsCount(attemptsCount-1);
				messageBot.setStatusProcessItemTaskEnum(StatusProcessItemTaskEnum.ERROR);
				taskManager.updateItemTask(messageBot);
				taskManager.updateTask(messageBot.getTaskId(), StatusProcessTaskEnum.ERROR);
				return;
			}

			producer.send(queueName, messageBot);

			return;
		}
		if (!Objects.isNull(messageBot.getStatuProcessEnum()))
			taskManager.updateItemTask(messageBot);
	}

	@Override
	public Optional<MessageBot> receiveMessage(String queueName) {
		return Optional.ofNullable(receiver.receive(queueName));
	}

}