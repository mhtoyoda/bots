package com.fiveware.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/parameter")
public class ParameterController {
	
	private static final Logger logger = LoggerFactory.getLogger(ParameterController.class);
	
	@GetMapping("/load")
	public ResponseEntity<Object> loadParametes(){
		logger.info("Carregando os parametros");
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/save")
	public ResponseEntity<Object> saveParametes(){
		logger.info("Gravando os parametros");
		return ResponseEntity.ok().build();
	}

}
