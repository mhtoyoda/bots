package com.fiveware.controller;

import com.fiveware.model.BotsMetric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import com.fiveware.service.PercentsService;

/**
 * Created by valdisnei on 7/11/17.
 */
@RestController
public class SocketController {

    @Autowired
    private PercentsService service;

    @Autowired
    private SimpMessagingTemplate template;

//    @GetMapping("/percents/{value}")
//    @SendTo("/topic/percents")
//    public Float getScores(@PathVariable Float value) {
////        Integer percents = service.percents();
//        template.convertAndSend("/topic/percents", value);
//        return value;
//    }

    @PostMapping("/bot-metric")
    @SendTo("/topic/percents")
    public ResponseEntity<BotsMetric> sendMetrics(@RequestBody BotsMetric value) {
        template.convertAndSend("/topic/percents", value);
        return ResponseEntity.ok(value);
    }

    @GetMapping("/")
    public String home(){
        return "index";
    }
}
