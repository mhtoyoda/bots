package com.fiveware.controller;

import com.fiveware.service.PercentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by valdisnei on 7/11/17.
 */
@RestController
public class SocketController {

    @Autowired
    private PercentsService service;

    @Autowired
    private SimpMessagingTemplate template;

    @GetMapping("/percents/{value}")
    @SendTo("/topic/percents")
    public Integer getScores(@PathVariable Integer value) {
//        Integer percents = service.percents();
        template.convertAndSend("/topic/percents", value);
        return value;
    }

    @GetMapping("/")
    public String home(){
        return "index";
    }
}
