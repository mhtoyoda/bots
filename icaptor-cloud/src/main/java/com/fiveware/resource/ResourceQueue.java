package com.fiveware.resource;

import com.fiveware.exception.MessageStatusBot;
import com.fiveware.messaging.BrokerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class ResourceQueue {

	static Logger logger = LoggerFactory.getLogger(ResourceQueue.class);
	
	@Autowired
	private BrokerManager brokerManager;
	
	@GetMapping("queue/{queue}")
	public ResponseEntity<Object> createQueue(@PathVariable String queue,		
			HttpServletRequest httpRequest) {
		try {			
			brokerManager.createQueue(queue);						
			return ResponseEntity.ok().body("QUEUE CREATED: "+queue);                    
		} catch (Exception e) {
			  return ResponseEntity.badRequest().
	                    body(new MessageStatusBot(HttpStatus.BAD_REQUEST.value(),e.getMessage()));
		}
	}
}
