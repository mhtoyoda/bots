package com.fiveware;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AgentIcaptorApplication {
	
	private final static Logger log = LoggerFactory.getLogger(AgentIcaptorApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(AgentIcaptorApplication.class, args);		
	}

}