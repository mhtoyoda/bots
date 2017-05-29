package com.fiveware.controller;

import com.fiveware.service.ServiceBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by valdisnei on 29/05/17.
 */

@RestController
public class AgentController {


    @Autowired
    private ServiceBot serviceBot;

    @GetMapping("/callBot/{cep}")
    public ResponseEntity<Object> callBot(@PathVariable String cep){

        Object o = serviceBot.callBot(cep);

        return ResponseEntity.ok(o);
    }
}
