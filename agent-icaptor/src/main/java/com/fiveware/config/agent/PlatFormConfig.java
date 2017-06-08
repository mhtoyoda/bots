package com.fiveware.config.agent;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fiveware.messaging.Producer;
import com.fiveware.messaging.QueueName;
import com.fiveware.messaging.TypeMessage;

/**
 * Created by valdisnei on 06/06/17.
 */
@Component
//@PropertySource(ignoreResourceNotFound=true,value="classpath:icaptor-platform.properties")
public class PlatFormConfig {
    static Logger logger= LoggerFactory.getLogger(PlatFormConfig.class);

//    @Value("${host}")
//    String host;

    @Autowired
    private Producer<String> producer;

    @PostConstruct
    public void up(){
        producer.send(QueueName.EVENTS.name(), TypeMessage.KEEP_ALIVE.name());
    }


//    @PreDestroy
//    public void down(){
//
//        logger.info("Server DOWN: {} ",response);
//    }


}
