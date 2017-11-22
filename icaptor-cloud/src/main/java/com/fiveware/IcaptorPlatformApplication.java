package com.fiveware;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class IcaptorPlatformApplication {
	
	static final Logger logger = LoggerFactory.getLogger(IcaptorPlatformApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(IcaptorPlatformApplication.class, args);
	}
	
	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}

}
