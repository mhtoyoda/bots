package com.fiveware.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class ForwardService {

	static Logger logger = LoggerFactory.getLogger(ForwardService.class);

	@Autowired
	private RestTemplate restTemplate;
	
	public ResponseEntity<Object> forwardGetApi(@PathVariable String botName, @PathVariable String endPoint,
			@PathVariable Object parameter,@PathVariable String serverName,  HttpServletRequest httpRequest) {
		return null;
	}

	public ResponseEntity<Object> forwardPostBot(@PathVariable String botName, @PathVariable String endPoint,
			@RequestBody Object parameter,@PathVariable String serverName, HttpServletRequest request) {
		return forwardGetApi(botName, endPoint, parameter,serverName,request);
	}
}
