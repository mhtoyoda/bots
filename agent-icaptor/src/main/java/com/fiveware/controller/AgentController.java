package com.fiveware.controller;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiveware.service.MessageBot;
import com.fiveware.service.ServiceBot;

/**
 * Created by valdisnei on 29/05/17.
 */

@RestController
@RequestMapping("/api")
public class AgentController {

    @Autowired
    public ServiceBot<Object> serviceBot;


    @GetMapping("/{botName}/{parameter}")
    public ResponseEntity<Object> getBot(@PathVariable String botName, @PathVariable Object parameter, HttpServletRequest request) {
        Object o = serviceBot.callBot(botName,parameter);


        if (Objects.isNull(o))
            return ResponseEntity.badRequest().body(
                    new MessageBot(HttpStatus.BAD_REQUEST.value(), "Bot Não Encontrado!"));

        return ResponseEntity.ok(o);
    }

    @PostMapping("/{botName}")
    public ResponseEntity<Object> postBot(@PathVariable String botName,@RequestBody Object parameter, HttpServletRequest request) {
        return getBot(botName,parameter,request);
    }
}


