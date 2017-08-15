package com.fiveware.scheduler;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fiveware.config.agent.AgentConfigProperties;
import com.fiveware.context.QueueContext;
import com.fiveware.exception.AttributeLoadException;
import com.fiveware.exception.ExceptionBot;
import com.fiveware.exception.UnRecoverableException;
import com.fiveware.messaging.Receiver;
import com.fiveware.model.Bot;
import com.fiveware.model.message.MessageBot;
import com.fiveware.processor.ProcessBot;
import com.fiveware.pulling.BrokerPulling;
import com.fiveware.service.ServiceAgent;

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
	
	@Autowired
	private QueueContext queueContext;
	
	@Scheduled(fixedDelayString = "${broker.queue.send.schedularTime}")
	public void process() throws ExceptionBot,UnRecoverableException {

		List<Bot> bots = serviceAgent.findBotsByAgent(data.getAgentName());
		bots.forEach(bot -> {
			String botName = bot.getNameBot();
			Set<String> queues = queueContext.getTasksQueues(botName);
			queues.stream().forEach(queue ->{
				pullMessage(botName, queue);				
			});
		});
	}

	/**
	 * Validar regras de bloqueio para pulling de fila
	 * 
	 */
	@Override
	public boolean canPullingMessage() {
		return queueContext.hasTask();
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