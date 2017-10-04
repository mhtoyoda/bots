package com.fiveware.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

	@GetMapping("/load-by-scope/{botName}/{scopeName}")
	public ResponseEntity<Object> loadParametesForAllUser(@PathVariable String botName, @PathVariable String scopeName) {
		logger.info("Carregando os parametros");

		List<Parameter> parameters = parameterService.getParametersByBotAndScopeName(botName, scopeName);
		return ResponseEntity.ok(parameters);
	}

	@GetMapping("/load-by-user/{botName}/{userId}")
	public ResponseEntity<Object> loadUserParametes(@PathVariable String botName, @PathVariable Long userId) {
		logger.info("Carregando os parametros");

		List<Parameter> parameters = parameterService.getParametersByBotAndUserId(botName, userId);
		return ResponseEntity.ok(parameters);
	}

	@PostMapping("/save-single")
	public ResponseEntity<Parameter> save(@RequestBody Parameter parameter) {
		Parameter saved = parameterService.save(parameter);
		return ResponseEntity.ok(saved);
	}

	@PostMapping("/save-several")
	public ResponseEntity<List<Parameter>> save(@RequestBody List<Parameter> parameters) {
		List<Parameter> saved = parameterService.save(parameters);
		return ResponseEntity.ok(saved);
	}

	@DeleteMapping("/delete-single")
	public ResponseEntity<Parameter> delete(@RequestBody Parameter parameter) {
		parameterService.delete(parameter);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/delete-several")
	public ResponseEntity<List<Parameter>> delete(@RequestBody List<Parameter> parameters) {
		parameterService.delete(parameters);
		return ResponseEntity.ok().build();
	}

}
