package com.fiveware.config.agent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.Lifecycle;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fiveware.messaging.Producer;
import com.fiveware.messaging.QueueName;
import com.fiveware.messaging.TypeMessage;
import com.fiveware.model.message.MessageAgent;

/**
 * Created by valdisnei on 06/06/17.
 */
@Component
public class PlatFormConfig implements Lifecycle {
    
	static Logger logger= LoggerFactory.getLogger(PlatFormConfig.class);
	
	@Autowired
	private AgentConfigProperties data;
	
	@Autowired
	private AgentListener agentListener;

    @Autowired
    @Qualifier("eventMessageProducer")
    private Producer<MessageAgent> producer;   

    private void sendMessage(TypeMessage typeMessage, String description) throws Exception {
    	MessageAgent message = new MessageAgent(data.getHost(), "Agent-"+agentListener.getAgentPort(), data.getIp(), agentListener.getAgentPort(), typeMessage, description);
    	producer.send(QueueName.EVENTS.name(), message);		
    }

	@Override
	public void start() {
		logger.debug("Start Agent Host:{}", data.getHost());		
	}

	@Override
	public void stop() {
		try {
			sendMessage(TypeMessage.STOP_AGENT, "Stop Agent!");
		} catch (Exception e) {
			logger.info("Error while Stopping Agent Host:{}", data.getHost());
		}		
	}
	
	@Scheduled(fixedDelay = 10000)
	public void keepAliveNotification() {
		try {
			sendMessage(TypeMessage.KEEP_ALIVE, "Keep Alive!");
		} catch (Exception e) {
			logger.info("Error while Starting Agent Host:{} IP: {}", data.getHost(),data.getIp());
		}
	}
	
	@Override
	public boolean isRunning() {		
		return true;
	}
}