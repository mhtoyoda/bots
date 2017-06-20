package com.fiveware;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(ignoreResourceNotFound=true,value="classpath:icaptor-platform.properties")
public class IcaptorAgentApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(IcaptorAgentApplication.class, args);	
	}

}