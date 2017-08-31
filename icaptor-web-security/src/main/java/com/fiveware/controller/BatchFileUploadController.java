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

import com.fiveware.model.Bot;
import com.fiveware.service.ServiceBot;

@RestController
@RequestMapping("/file-upload")
public class BatchFileUploadController {

	private static final Logger logger = LoggerFactory.getLogger(BatchFileUploadController.class);

	@Autowired
	private ServiceBot botService;

	@GetMapping("/bot/user/{id}")
	public ResponseEntity<Object> loadAvailableBots(@PathVariable Long id) {
		List<Bot> bots = botService.findAll();
		logger.info("Carregando [{}] bots disponiveis para o usuario [{}]", bots.size(), id);
		return ResponseEntity.ok(bots);
	}

}
