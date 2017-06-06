package com.fiveware.config.agent;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Created by valdisnei on 06/06/17.
 */
@Component
@PropertySource(ignoreResourceNotFound=true,value="classpath:icaptor-platform.properties")
public class PlatFormConfig {
    static Logger logger= LoggerFactory.getLogger(PlatFormConfig.class);

    @Value("${host}")
    String host;
    @PostConstruct
    public void up(){
        RestTemplate testRestTemplate = new RestTemplate();
        ResponseEntity<String> response = testRestTemplate.getForEntity(
                "http://"+host + "/up", String.class);

        logger.info("Server UP: {} ",response);
    }


    @PreDestroy
    public void down(){
        RestTemplate testRestTemplate = new RestTemplate();
        ResponseEntity<String> response = testRestTemplate.getForEntity(
                "http://"+host + "/down", String.class);

        logger.info("Server DOWN: {} ",response);
    }


}
