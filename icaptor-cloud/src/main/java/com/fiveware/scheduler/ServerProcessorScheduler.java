package com.fiveware.scheduler;

import java.util.List;
import java.util.Optional;

import com.fiveware.util.FileUtil;
import com.fiveware.util.ListJoinUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fiveware.ServerConfig;
import com.fiveware.dao.AgentDAO;
import com.fiveware.dao.ServerDAO;
import com.fiveware.messaging.Receiver;
import com.fiveware.model.Agent;
import com.fiveware.model.Bot;
import com.fiveware.model.MessageBot;
import com.fiveware.pulling.BrokerPulling;

@Component
public class ServerProcessorScheduler extends BrokerPulling<MessageBot>{

	private static Logger log = LoggerFactory.getLogger(ServerProcessorScheduler.class);

	@Autowired
	private Receiver<MessageBot> receiver;

	@Autowired
	private ServerDAO serverDAO;

	@Autowired
	private AgentDAO agentDAO;

	@Autowired
	private ServerConfig serverConfig;


	@Autowired
	private FileUtil fileUtil;

	@Scheduled(fixedDelay = 60000)
	public void process(){
		List<Agent> agents = serverDAO.getAllAgents(serverConfig.getServer().getName());
		agents.forEach(agent -> {
			log.info("Pulling Message [Agent]: {}", agent.getNameAgent());
			List<Bot> bots = agentDAO.findBotsByAgent(agent.getNameAgent());
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
		List<String> linesResult = obj.getLineResult();
		log.debug("Total de Linhas resultado: {}", linesResult.size());
		//FIXME Consolidar linhas de resultado para gerar o arquivo final

		fileUtil.writeFile(linesResult,obj);
	}

	@Override
	public Optional<MessageBot> receiveMessage(String queueName) {
		return Optional.ofNullable(receiver.receive(queueName));
	}

}