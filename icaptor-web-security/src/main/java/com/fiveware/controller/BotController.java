package com.fiveware.controller;

import com.fiveware.model.Bot;
import com.fiveware.service.ServiceBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/bot")
public class BotController {

	private static final Logger logger = LoggerFactory.getLogger(BotController.class);

	@Autowired
	private ServiceBot serviceBot;
	
	@GetMapping
	public ResponseEntity<Object> loadBotInfo() {
		logger.info("Carregando as informacoes do bot.");
		List<Bot> all = serviceBot.findAll();
		return ResponseEntity.ok().body(all);
	}

}
