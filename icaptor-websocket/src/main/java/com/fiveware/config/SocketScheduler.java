package com.fiveware.config;

import com.fiveware.service.PercentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Created by valdisnei on 7/11/17.
 */
@Configuration
@EnableScheduling
public class SocketScheduler {

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    PercentsService service;

    @Scheduled(fixedRate = 5000)
    public void publishUpdates(){

//        template.convertAndSend("/topic/percents", service.percents());

    }

}
