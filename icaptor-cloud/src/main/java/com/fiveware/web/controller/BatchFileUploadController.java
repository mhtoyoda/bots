package com.fiveware.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/batchUpload")
public class BatchFileUploadController {

	private static final Logger logger = LoggerFactory.getLogger(BatchFileUploadController.class);
	
	@GetMapping("/loadAvailable")
	public ResponseEntity<Object> loadAvailableBots(){
		logger.info("Carregando a lista de bots disponiveis para esse usuario");
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/createTask")
	public ResponseEntity<Object> createTask(){
		logger.info("Criando uma nova task");
		return ResponseEntity.ok().build();
	}

}
