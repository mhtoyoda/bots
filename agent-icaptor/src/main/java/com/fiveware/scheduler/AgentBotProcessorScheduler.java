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

import com.fiveware.dao.AgentDAO;
import com.fiveware.exception.AttributeLoadException;
import com.fiveware.exception.ExceptionBot;
import com.fiveware.loader.LoadFile;
import com.fiveware.messaging.Receiver;
import com.fiveware.model.Bot;
import com.fiveware.model.MessageInputDictionary;
import com.fiveware.pulling.BrokerPulling;
import com.google.common.collect.Lists;

@Component
public class AgentBotProcessorScheduler extends BrokerPulling<MessageInputDictionary>{

	private static Logger log = LoggerFactory.getLogger(AgentBotProcessorScheduler.class);
	
	@Value("${agent}")
	private String nameAgent;
	
	@Autowired
	private Receiver<MessageInputDictionary> receiver;
	
	@Autowired
	private AgentDAO agentDAO;
	
	@Autowired
	private LoadFile loadFile;
	
	@Scheduled(fixedDelay = 60000)
	public void process(){
		List<Bot> bots = agentDAO.findBotsByAgent(nameAgent);
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
	public void processMessage(String botName, MessageInputDictionary obj) {
		List<String> lines = Lists.newArrayList(obj.getLine());
		try {
			loadFile.executeLoad(botName, lines);
			log.debug("[BOT]: {}", botName);
		} catch (ClassNotFoundException | IOException | AttributeLoadException | ExceptionBot e) {
			log.error("Error - {}", e.getMessage());
		}
	}

	@Override
	public Optional<MessageInputDictionary> receiveMessage(String queueName) {
		return Optional.ofNullable(receiver.receive(queueName));
	}
}