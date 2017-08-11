package com.fiveware.scheduler;

import com.fiveware.config.agent.AgentConfigProperties;
import com.fiveware.exception.AttributeLoadException;
import com.fiveware.exception.ExceptionBot;
import com.fiveware.exception.UnRecoverableException;
import com.fiveware.messaging.Receiver;
import com.fiveware.model.Bot;
import com.fiveware.model.MessageBot;
import com.fiveware.processor.ProcessBot;
import com.fiveware.pulling.BrokerPulling;
import com.fiveware.service.ServiceAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class AgentBotProcessorScheduler extends BrokerPulling<MessageBot>{

	private static Logger log = LoggerFactory.getLogger(AgentBotProcessorScheduler.class);
	
	@Autowired
	private AgentConfigProperties data;
	
	@Autowired
	@Qualifier("eventBotReceiver")
	private Receiver<MessageBot> receiver;
	
	@Autowired
	private ServiceAgent serviceAgent;
	
	@Autowired
	@Qualifier("processBotCSV")
	private ProcessBot<MessageBot> processBotCSV;
	
	@Scheduled(fixedDelayString = "${broker.queue.send.schedularTime}")
	public void process() throws ExceptionBot,UnRecoverableException {

		List<Bot> bots = serviceAgent.findBotsByAgent(data.getAgentName());
		bots.forEach(bot -> {
			String botName = bot.getNameBot();
			//FIXME sera alterado para olhar o contexto de tasks existentes
			//FIXME nameQueue sera exemplo 'consultaCEP.1.IN'
			String nameQueue = botName+".1.IN";
			pullMessage(botName, nameQueue);
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
	public void processMessage(String botName, MessageBot obj){
		try {
			processBotCSV.execute(botName, obj);
		} catch (IOException e) {
			log.error("{}",e);
		} catch (AttributeLoadException e) {
			log.error("{}",e);
		} catch (ClassNotFoundException e) {
			log.error("{}",e);
		} catch (ExceptionBot exceptionBot) {
			log.error("{}",exceptionBot);
		} catch (UnRecoverableException recoverable) {
			log.error("{}",recoverable);
		}finally {
			log.debug("[BOT]: {}", botName);
		}
	}

	@Override
	public Optional<MessageBot> receiveMessage(String queueName) {
		return Optional.ofNullable(receiver.receive(queueName));
	}



}