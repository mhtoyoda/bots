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
    public ServiceBot serviceBot;


    @GetMapping("/{endPoint}/{parameter}")
    public ResponseEntity<Object> getBot(@PathVariable String endPoint, @PathVariable Object parameter, HttpServletRequest request) {
        Object o = serviceBot.callBot(endPoint,parameter);


        if (Objects.isNull(o))
            return ResponseEntity.badRequest().body(
                    new MessageBot(HttpStatus.BAD_REQUEST.value(), "Bot Não Encontrado!"));

        return ResponseEntity.ok(o);
    }

    @PostMapping("/{endPoint}")
    public ResponseEntity<Object> postBot(@PathVariable String endPoint,@RequestBody Object parameter, HttpServletRequest request) {
        return getBot(endPoint,parameter,request);
    }
}


