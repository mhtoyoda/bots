package com.fiveware.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bot")
public class BotController {

	private static final Logger logger = LoggerFactory.getLogger(BotController.class);
	
	@GetMapping("/loadBotInfo")
	public ResponseEntity<Object> loadBotInfo() {
		logger.info("Carregando as informacoes do bot.");
		return ResponseEntity.ok().build();
	}

}
