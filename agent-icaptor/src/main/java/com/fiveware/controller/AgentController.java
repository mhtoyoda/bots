package com.fiveware.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fiveware.ApiBot;
import com.fiveware.service.ServiceBot;

/**
 * Created by valdisnei on 29/05/17.
 */

@RestController
public class AgentController implements ApiBot<String,Object>{

    @Autowired
    public ServiceBot<String> serviceBot;

	@SuppressWarnings("rawtypes")
	@Override
    public ResponseEntity<Object> callBot(@PathVariable  String parameter) {
        Class classLoader = serviceBot.loadClassLoader();
    	Object o = serviceBot.callBot(classLoader, parameter);
        return ResponseEntity.ok(o);
    }
}
