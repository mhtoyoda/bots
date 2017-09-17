package com.fiveware;

import com.fiveware.config.ICaptorApiProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpManagementOperations;
import org.springframework.amqp.rabbit.core.RabbitManagementTemplate;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private ICaptorApiProperty iCaptorApiProperty;
	
	@Bean
	public AmqpManagementOperations amqpManagementOperations(){
		String uri = String.format("http://%s:%d/api/", iCaptorApiProperty.getBroker().getHost(), iCaptorApiProperty.getBroker().getPort());
		return new RabbitManagementTemplate(uri, iCaptorApiProperty.getBroker().getUser(), iCaptorApiProperty.getBroker().getPass());
	}



}
