package com.fiveware.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/controlpanel")
public class ControlPanelController {

	private static final Logger logger = LoggerFactory.getLogger(ControlPanelController.class);
	
	@GetMapping("/loadUserInfo")
	public ResponseEntity<Object> loadUserInfo() {
		logger.info("Carregando as informacoes do usuario.");
		return ResponseEntity.ok().build();
	}

	@GetMapping("/loadTasks")
	public ResponseEntity<Object> loadTasks() {
		logger.info("Carregando as tasks.");
		return ResponseEntity.ok().build();
	}

	@GetMapping("/loadRecentActivities")
	public ResponseEntity<Object> loadRecentActivities() {
		return ResponseEntity.ok().build();
	}
	@GetMapping("/loadFilterOptions")
	public ResponseEntity<Object> loadFilterOptions() {
		return ResponseEntity.ok().build();
	}
}
