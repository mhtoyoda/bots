package com.fiveware.config.agent;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.CollectionUtils;
import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.Lifecycle;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fiveware.loader.ClassLoaderConfig;
import com.fiveware.messaging.Producer;
import com.fiveware.messaging.TypeMessage;
import com.fiveware.model.BotClassLoaderContext;
import com.fiveware.model.MessageAgent;
import com.fiveware.model.MessageAgentBot;

/**
 * Created by valdisnei on 06/06/17.
 */
@Component
//@PropertySource(ignoreResourceNotFound=true,value="classpath:icaptor-platform.properties")
public class PlatFormConfig implements Lifecycle {
    
	static Logger logger= LoggerFactory.getLogger(PlatFormConfig.class);
	
	@Value("${agent:agentTeste}")
	String agent;
	
	@Value("${ip:127.0.0.1}")
	String ip;
	
    @Value("${host}")
    String host;

    @Autowired
    @Qualifier("eventMessageProducer")
    private Producer<MessageAgent> producer;
    
    @Autowired
    @Qualifier("mapClassLoaderConfig")
    private ClassLoaderConfig classLoaderConfig;

    private void sendMessage(TypeMessage typeMessage, String description) throws Exception {
    	MessageAgent message = new MessageAgent(host, agent, ip, typeMessage, description);
    	producer.send(message);		
    }

    @PostConstruct
    public void up(){
    	List<MessageAgentBot> list = Lists.newArrayList();
    	List<BotClassLoaderContext> bots = classLoaderConfig.getAll();
    	if(CollectionUtils.isNotEmpty(bots)){
    		bots.forEach(bot -> {
    			MessageAgentBot messageAgentBot = new MessageAgentBot();
    			messageAgentBot.setEndpoint(bot.getEndpoint());
    			messageAgentBot.setNameBot(bot.getNameBot());
    			messageAgentBot.setNameMethod(bot.getMethod());
    			list.add(messageAgentBot);
    		});
    	}
    	  
    	MessageAgent message = new MessageAgent(host, agent, ip, TypeMessage.START_AGENT, "Start Agent!");
    	message.setMessageAgentBots(list);
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