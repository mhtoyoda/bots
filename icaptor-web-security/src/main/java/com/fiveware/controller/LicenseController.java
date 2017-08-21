package com.fiveware.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/license")
public class LicenseController {

	private static final Logger logger = LoggerFactory.getLogger(LicenseController.class);
	
	@GetMapping("/load")
	public ResponseEntity<Object> loadLastLincense(){
		logger.info("Carregando as informacoes da ultima licenca.");
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/save")
	public ResponseEntity<Object> saveNewLincense(){
		logger.info("Carregando as informacoes da ultima licenca.");
		return ResponseEntity.ok().build();
	}
}
