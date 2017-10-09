package com.fiveware.controller;

import com.fiveware.model.Parameter;
import com.fiveware.model.TypeParameter;
import com.fiveware.service.ServiceParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

	@GetMapping("/scope-parameter/{scopeName}")
	public ResponseEntity<Object> listParametersByScope(@PathVariable String scopeName) {
		logger.info("Carregando os parametros");

		List<Parameter> parameters = parameterService.listParametersByScope(scopeName);
		return ResponseEntity.ok(parameters);
	}

	@GetMapping("/type")
	public ResponseEntity<Object> getTypeParameterAll() {
		logger.info("Carregando os TypeParameters");

		List<TypeParameter> parameters = parameterService.getTypeParameterAll();
		return ResponseEntity.ok(parameters);
	}


	@GetMapping("/load-by-user/{botName}/{userId}")
	public ResponseEntity<Object> loadUserParametes(@PathVariable String botName, @PathVariable Long userId) {
		logger.info("Carregando os parametros");

		List<Parameter> parameters = parameterService.getParametersByBotAndUserId(botName, userId);
		return ResponseEntity.ok(parameters);
	}

	@GetMapping
	public ResponseEntity<Object> getParametersAll() {
		logger.info("Carregando os parametros");

		Iterable<Parameter> parameters = parameterService.getParametersAll();
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

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		parameterService.delete(id);
	}

	@DeleteMapping("/delete-several")
	public ResponseEntity<List<Parameter>> delete(@RequestBody List<Parameter> parameters) {
		parameterService.delete(parameters);
		return ResponseEntity.ok().build();
	}

}
