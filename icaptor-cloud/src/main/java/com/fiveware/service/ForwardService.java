package com.fiveware.service;

import com.fiveware.config.ServerConfig;
import com.fiveware.discovery.AgentServiceDiscovery;
import com.fiveware.exception.AgentBotNotFoundException;
import com.fiveware.exception.MessageStatusBot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@RestController
@RequestMapping("/api")
public class ForwardService {

	static Logger logger = LoggerFactory.getLogger(ForwardService.class);

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
