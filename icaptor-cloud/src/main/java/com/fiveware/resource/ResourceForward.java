package com.fiveware.resource;

import com.fiveware.exception.AgentBotNotFoundException;
import com.fiveware.exception.MessageStatusBot;
import com.fiveware.service.discovery.AgentServiceDiscovery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@RestController
@RequestMapping("/api")
public class ResourceForward {

	static Logger logger = LoggerFactory.getLogger(ResourceForward.class);

	@Autowired
	private AgentServiceDiscovery agentServiceDiscovery;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@PostMapping({"/{botName}/{endPoint}/{parameter}/{serverName}","/{botName}/{endPoint}/{serverName}"})
	public ResponseEntity<Object> forwardGetApi(@PathVariable String botName, @PathVariable String endPoint,
			@PathVariable Object parameter,@PathVariable String serverName,  HttpServletRequest httpRequest) {
		try {
			String urlService = agentServiceDiscovery.getUrlService(serverName, botName, endPoint);
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

}
