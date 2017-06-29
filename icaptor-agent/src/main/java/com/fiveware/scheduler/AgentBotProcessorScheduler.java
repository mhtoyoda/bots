package com.fiveware.scheduler;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fiveware.exception.AttributeLoadException;
import com.fiveware.exception.ExceptionBot;
import com.fiveware.loader.ProcessBotLoader;
import com.fiveware.messaging.Receiver;
import com.fiveware.model.Bot;
import com.fiveware.model.MessageBot;
import com.fiveware.pulling.BrokerPulling;
import com.fiveware.repository.AgentRepository;

@Component
public class AgentBotProcessorScheduler extends BrokerPulling<MessageBot>{

	private static Logger log = LoggerFactory.getLogger(AgentBotProcessorScheduler.class);
	
	@Value("${agent}")
	private String nameAgent;
	
	@Autowired
	private Receiver<MessageBot> receiver;
	
	@Autowired
	private AgentRepository agentRepository;
	
	@Autowired
	private ProcessBotLoader processBotLoader;
	
	@Scheduled(fixedDelayString = "${broker.queue.send.schedularTime}")
	public void process(){
		List<Bot> bots = agentRepository.findBotsByAgent(nameAgent);
		bots.forEach(bot -> {
			String botName = bot.getNameBot();
			String nameQueue = botName+"_IN";			 
			pullMessage(botName, nameQueue);
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
		try {
			processBotLoader.executeLoad(botName, obj);
			log.debug("[BOT]: {}", botName);
		} catch (ClassNotFoundException | IOException | AttributeLoadException | ExceptionBot e) {
			log.error("Error - {}", e.getMessage());
		}
	}

	@Override
	public Optional<MessageBot> receiveMessage(String queueName) {
		return Optional.ofNullable(receiver.receive(queueName));
	}
}