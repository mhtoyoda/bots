package com.fiveware.controller;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fiveware.ServerConfig;
import com.fiveware.exception.AgentBotNotFoundException;
import com.fiveware.exception.MessageStatusBot;
import com.fiveware.service.AgentServiceDiscovery;

@RestController
@RequestMapping("/api")
public class ForwardController {

	static Logger logger = LoggerFactory.getLogger(ForwardController.class);

	@Autowired
	private ServerConfig serverConfig;
	
	@Autowired
	private AgentServiceDiscovery agentServiceDiscovery;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@GetMapping("/{botName}/{endPoint}/{parameter}")
	public ResponseEntity<Object> forwardGetApi(@PathVariable String botName, @PathVariable String endPoint,
			@PathVariable Object parameter, HttpServletRequest httpRequest) {
		try {
			String urlService = agentServiceDiscovery.getUrlService(serverConfig.getServer().getName(), botName, endPoint);
			urlService = urlService.concat("/"+parameter);
			
			HttpHeaders headers = new HttpHeaders();
		    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		    HttpEntity<Object> entity = new HttpEntity<Object>(parameter, headers);
		     
			ResponseEntity<Object> result = restTemplate.exchange(urlService, HttpMethod.GET, entity, Object.class);
			return result;
		} catch (AgentBotNotFoundException e) {
			  return ResponseEntity.badRequest().
	                    body(new MessageStatusBot(HttpStatus.BAD_REQUEST.value(),e.getMessage()));
		}

	}

	@PostMapping("/{botName}/{endPoint}")
	public ResponseEntity<Object> forwardPostBot(@PathVariable String botName, @PathVariable String endPoint,
			@RequestBody Object parameter, HttpServletRequest request) {		
		return forwardGetApi(botName, endPoint, parameter, request);
	}
}
