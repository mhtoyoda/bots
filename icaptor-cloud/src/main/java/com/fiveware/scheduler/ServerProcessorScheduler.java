package com.fiveware.scheduler;

import com.fiveware.config.ServerConfig;
import com.fiveware.messaging.Receiver;
import com.fiveware.model.MessageBot;
import com.fiveware.model.entities.Agent;
import com.fiveware.model.entities.Bot;
import com.fiveware.pulling.BrokerPulling;
import com.fiveware.service.ServiceAgent;
import com.fiveware.service.ServiceServer;
import com.fiveware.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ServerProcessorScheduler extends BrokerPulling<MessageBot>{

	private static Logger log = LoggerFactory.getLogger(ServerProcessorScheduler.class);

	@Autowired
	private Receiver<MessageBot> receiver;

	@Autowired
	private ServiceServer serverRepository;

	@Autowired
	private ServiceAgent agentRepository;

	@Autowired
	private ServerConfig serverConfig;


	@Autowired
	private FileUtil fileUtil;

	@Scheduled(fixedDelayString = "${broker.queue.send.schedularTime}")
	public void process(){
		List<Agent> agents = serverRepository.getAllAgent(serverConfig.getServer().getName());
		agents.forEach(agent -> {
			log.info("Pulling Message [Agent]: {}", agent.getNameAgent());
			List<Bot> bots = agentRepository.findBotsByAgent(agent.getNameAgent());
			bots.forEach(bot -> {
				String botName = bot.getNameBot();
				String nameQueue = botName+"_OUT";
				pullMessage(botName, nameQueue);
			});
		});
	}

	/**
	 * Validar regras de bloqueio para pulling de fila
	 *
	 */
	@Override
	public boolean canPullingMessage(String queue) {
		return true;
	}

	/**
	 * Processa mensagem recebida do Broker
	 */
	@Override
	public void processMessage(String botName, MessageBot obj) {
		log.debug("Total de Linhas resultado: {}", obj.getLineResult());
		//FIXME Consolidar linhas de resultado para gerar o arquivo final

		fileUtil.writeFile(obj);
	}

	@Override
	public Optional<MessageBot> receiveMessage(String queueName) {
		return Optional.ofNullable(receiver.receive(queueName));
	}

}