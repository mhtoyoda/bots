package com.fiveware.controller;

import com.fiveware.ApiBot;
import com.fiveware.service.ServiceBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by valdisnei on 29/05/17.
 */

@RestController
public class AgentController implements ApiBot<String,Object>{



    @Autowired
    public ServiceBot serviceBot;

    @Override
    public ResponseEntity<Object> callBot(@PathVariable  String parameter) {
        Object o = serviceBot.callBot(parameter);
        return ResponseEntity.ok(o);
    }
}
