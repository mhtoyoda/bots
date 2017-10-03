package com.fiveware.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiveware.model.Parameter;
import com.fiveware.service.ServiceParameter;

@RestController
@RequestMapping("/parameter")
public class ParameterController {
	
	private static final Logger logger = LoggerFactory.getLogger(ParameterController.class);
	
	@Autowired
	private ServiceParameter parameterService;
	
	@GetMapping("/load/{botName}/{scopeName}")
	public ResponseEntity<Object> loadParametesForAllUser(@PathVariable String botName, @PathVariable String scopeName){
		logger.info("Carregando os parametros");
		
		List<Parameter> parameters = parameterService.getParametersByBotAndScopeName(botName, scopeName);
		return ResponseEntity.ok(parameters);
	}
	
	@GetMapping("/load/{botName}/{userId}")
	public ResponseEntity<Object> loadUserParametes(@PathVariable String botName, @PathVariable Long userId){
		logger.info("Carregando os parametros");
		
		List<Parameter> parameters = parameterService.getParametersByBotAndUserId(botName, userId);
		return ResponseEntity.ok(parameters);
	}

	
	@GetMapping("/save")
	public ResponseEntity<Object> saveParametes(){
		logger.info("Gravando os parametros");
		return ResponseEntity.ok().build();
	}

}
