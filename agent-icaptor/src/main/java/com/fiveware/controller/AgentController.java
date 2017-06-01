package com.fiveware.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiveware.exception.ExceptionBot;
import com.fiveware.exception.MessageBot;
import com.fiveware.service.ServiceBot;

/**
 * Created by valdisnei on 29/05/17.
 */

@RestController
@RequestMapping("/api")
public class AgentController {

    @Autowired
    @Qualifier("rest")
    public ServiceBot serviceBot;


    @GetMapping("/{botName}/{endPoint}/{parameter}")
    public ResponseEntity<Object> getBot(@PathVariable String botName,@PathVariable String endPoint,
                                         @PathVariable Object parameter,HttpServletRequest request) {
        Object obj = null;
        try {
            obj = serviceBot.callBot(botName,endPoint,parameter);
        } catch (ExceptionBot e) {
            return ResponseEntity.badRequest().
                    body(new MessageBot(HttpStatus.BAD_REQUEST.value(),e.getMessage()));
        }

        return ResponseEntity.ok(obj);
    }

    @PostMapping("/{botName}/{endPoint}")
    public ResponseEntity<Object> postBot(@PathVariable String botName,
                                          @PathVariable String endPoint,
                                          @RequestBody Object parameter,
                                          HttpServletRequest request) {
        return getBot(botName,endPoint,parameter,request);
    }

    @GetMapping("/")
    public ResponseEntity<Object> get() {
        return ResponseEntity.ok("Hello World");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<MessageBot> illegalArgumentExceptional(IllegalArgumentException e) {
        return ResponseEntity.badRequest().
                body(new MessageBot(HttpStatus.BAD_REQUEST.value(),e.getMessage()));
    }
}


