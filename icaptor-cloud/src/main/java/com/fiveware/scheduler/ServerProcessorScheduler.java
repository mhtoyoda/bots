package com.fiveware.scheduler;

import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fiveware.dao.AgentDAO;
import com.fiveware.dao.ServerDAO;
import com.fiveware.messaging.Receiver;
import com.fiveware.model.Agent;
import com.fiveware.model.Bot;
import com.fiveware.model.MessageInputDictionary;
import com.fiveware.pulling.BrokerPulling;

@Component
public class ServerProcessorScheduler extends BrokerPulling<MessageInputDictionary>{

	private static Logger log = LoggerFactory.getLogger(ServerProcessorScheduler.class);
	
	@Autowired
	private Receiver<MessageInputDictionary> receiver;
	
	@Autowired
	private ServerDAO serverDAO;
	
	@Autowired
	private AgentDAO agentDAO;
	
	@Scheduled(fixedDelay = 60000)
	public void process(){
		List<Agent> agents = serverDAO.getAllAgents("serverTeste");
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
	public void processMessage(String botName, MessageInputDictionary obj) {
		//DADOS FAKE
		//FIXME substituir pelo (obj.getResult), contendo linhas do resultado do Bot
		List<String> linesResult = Lists.newArrayList("Avenida Paulista, Centro, São Paulo, 03510-000");
		log.debug("Total de Linhas resultado: {}", linesResult.size());
		//FIXME Consolidar linhas de resultado para gerar o arquivo final
	}

	@Override
	public Optional<MessageInputDictionary> receiveMessage(String queueName) {
		return Optional.ofNullable(receiver.receive(queueName));
	}
}