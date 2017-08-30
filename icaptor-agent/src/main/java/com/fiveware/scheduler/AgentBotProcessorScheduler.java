package com.fiveware.scheduler;

import com.fiveware.config.agent.AgentConfigProperties;
import com.fiveware.config.agent.AgentListener;
import com.fiveware.context.QueueContext;
import com.fiveware.exception.AttributeLoadException;
import com.fiveware.exception.ParameterInvalidException;
import com.fiveware.exception.RuntimeBotException;
import com.fiveware.messaging.Producer;
import com.fiveware.messaging.QueueName;
import com.fiveware.messaging.Receiver;
import com.fiveware.messaging.TypeMessage;
import com.fiveware.model.Bot;
import com.fiveware.model.message.MessageAgent;
import com.fiveware.model.message.MessageBot;
import com.fiveware.processor.ProcessBot;
import com.fiveware.pulling.BrokerPulling;
import com.fiveware.service.ServiceAgent;
import com.fiveware.service.ServiceSocket;
import com.google.common.base.MoreObjects;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

@Component
public class AgentBotProcessorScheduler extends BrokerPulling<MessageBot> {

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

    @Autowired
    private AgentListener agentListener;

    @Autowired
    @Qualifier("eventMessageProducer")
    private Producer<MessageAgent> producer;

    @Autowired
    private ServiceSocket serviceSocket;

    @Scheduled(fixedDelayString = "${broker.queue.send.schedularTime}")
    public void process() throws RuntimeBotException {
        List<Bot> bots = serviceAgent.findBotsByAgent(data.getAgentName());
        bots.forEach(this::accept);
    }

    private void accept(Bot bot) {
        AtomicInteger atomicInteger = new AtomicInteger(0);

        String botName = bot.getNameBot();
        queueContext.setKey(botName);
        queueContext.setKeyValue(data.getAgentName());
        Set<String> queues = MoreObjects.firstNonNull(queueContext.getTasksQueues(botName, data.getAgentName()),
                                                      Sets.newHashSet());
            try {
                queues.stream().
                        forEach(queue -> {

                            serviceSocket.setPercent(atomicInteger.getAndIncrement());

                            pullMessage(botName, queue);
                        });
            } catch (RuntimeBotException exceptionBot) {
                notifyServerPurgeQueues(bot.getNameBot(), data.getAgentName());
            }
    }

    /**
     * Validar regras de bloqueio para pulling de fila
     */
    @Override
    public boolean canPullingMessage() {
        return queueContext.hasTask();
    }

    /**
     * Processa mensagem recebida do Broker
     */
    @Override
    public void processMessage(String botName, MessageBot obj) throws RuntimeBotException {
        try {
            processBotCSV.execute(botName, obj);
        } catch (IOException e) {
            log.error("{}", e);
        } catch (AttributeLoadException e) {
            log.error("{}", e);
        } catch (ClassNotFoundException e) {
            log.error("{}", e);
        } catch (ParameterInvalidException e) {
        	log.error("{}", e);
		} finally {
            log.debug("[BOT]: {}", botName);
        }
    }

    @Override
    public Optional<MessageBot> receiveMessage(String queueName) {
        return Optional.ofNullable(receiver.receive(queueName));
    }

    private void notifyServerPurgeQueues(String nameBot, String nameAgent) {
        Set<String> queues = queueContext.getTasksQueues(nameBot);

        MessageAgent message = new MessageAgent(data.getHost(), nameAgent,
                data.getIp(), agentListener.getAgentPort(),
                TypeMessage.PURGE_QUEUES, "Purge Queues!");
        message.setNameQueues(queues);

        producer.send(QueueName.EVENTS.name(), message);

        queues.forEach(new Consumer<String>() {
            @Override
            public void accept(String nameQueue) {
                queueContext.removeQueueInContext(nameBot, nameQueue);
            }
        });

    }

}