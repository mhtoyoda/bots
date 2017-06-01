package com.fiveware.controller;

import javax.servlet.http.HttpServletRequest;

import com.fiveware.exception.ExceptionBot;
import com.fiveware.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by valdisnei on 29/05/17.
 */

@RestController
@RequestMapping("/api")
public class AgentController {

    @Autowired
    @Qualifier("rest")
    public ServiceBot<Object> serviceBot;


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


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<MessageBot> illegalArgumentExceptional(IllegalArgumentException e) {
        return ResponseEntity.badRequest().
                body(new MessageBot(HttpStatus.BAD_REQUEST.value(),e.getMessage()));
    }
}


