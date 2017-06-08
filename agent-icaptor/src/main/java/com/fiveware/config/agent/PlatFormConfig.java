package com.fiveware.config.agent;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.Lifecycle;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fiveware.messaging.Producer;
import com.fiveware.messaging.TypeMessage;
import com.fiveware.model.MessageAgent;

/**
 * Created by valdisnei on 06/06/17.
 */
@Component
@PropertySource(ignoreResourceNotFound=true,value="classpath:icaptor-platform.properties")
public class PlatFormConfig implements Lifecycle {
    
	static Logger logger= LoggerFactory.getLogger(PlatFormConfig.class);

    @Value("${host}")
    String host;

    @Autowired
    @Qualifier("eventMessageProducer")
    private Producer<MessageAgent> producer;

    private void sendMessage(TypeMessage typeMessage, String description) throws Exception {
    	MessageAgent message = new MessageAgent(host, typeMessage, description);
    	producer.send(message);		
    }

    @PostConstruct
    public void up(){
    	MessageAgent message = new MessageAgent(host, TypeMessage.START_AGENT, "Start Agent!");
        producer.send(message);
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