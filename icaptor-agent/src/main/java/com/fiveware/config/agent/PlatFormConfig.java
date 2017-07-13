package com.fiveware.config.agent;

import com.fiveware.messaging.Producer;
import com.fiveware.messaging.QueueName;
import com.fiveware.messaging.TypeMessage;
import com.fiveware.model.MessageAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.Lifecycle;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by valdisnei on 06/06/17.
 */
@Component
public class PlatFormConfig implements Lifecycle {
    
	static Logger logger= LoggerFactory.getLogger(PlatFormConfig.class);
	
	@Value("${icaptor.agent.name}")
	String agent;
	
	@Value("${icaptor.server.ip}")
	String ip;
	
    @Value("${icaptor.server.host}")
    String host;

    @Autowired
    @Qualifier("eventMessageProducer")
    private Producer<MessageAgent> producer;   

    private void sendMessage(TypeMessage typeMessage, String description) throws Exception {
    	MessageAgent message = new MessageAgent(host, agent, ip, typeMessage, description);
    	producer.send(QueueName.EVENTS.name(), message);		
    }

    @PostConstruct
    public void up(){    	    	  
    	MessageAgent message = new MessageAgent(host, agent, ip, TypeMessage.START_AGENT, "Start Agent!");    
        producer.send(QueueName.EVENTS.name(), message);
    }

	@Override
	public void start() {
		logger.debug("Start Agent Host:{}", host);		
	}

	@Override
	public void stop() {
		try {
			sendMessage(TypeMessage.STOP_AGENT, "Stop Agent!");
		} catch (Exception e) {
			logger.info("Error while Stopping Agent Host:{}", host);
		}		
	}
	
	@Scheduled(fixedDelay = 60000)
	public void keepAliveNotification() {
		try {
			sendMessage(TypeMessage.KEEP_ALIVE, "Keep Alive!");
		} catch (Exception e) {
			logger.info("Error while Starting Agent Host:{}", host);
		}
	}
	
	@Override
	public boolean isRunning() {		
		return true;
	}
}