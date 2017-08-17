package com.fiveware.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agent")
public class AgentController {

	private static final Logger logger = LoggerFactory.getLogger(AgentController.class);
	
	@GetMapping("/loadAgentInfo")
	public ResponseEntity<Object> loadAgentInfo() {
		logger.info("Carregando as informacoes dos agents.");
		return ResponseEntity.ok().build();
	}
	
}
