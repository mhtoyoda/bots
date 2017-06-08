package com.fiveware.config.agent;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import com.fiveware.messaging.Producer;
import com.fiveware.messaging.TypeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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
    private Producer producer;

    @PostConstruct
    public void up(){
        producer.send(TypeMessage.KEEP_ALIVE,"Im Alive!");
    }


//    @PreDestroy
//    public void down(){
//
//        logger.info("Server DOWN: {} ",response);
//    }


}
