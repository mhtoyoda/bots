package com.fiveware.config;

import org.springframework.amqp.core.AmqpManagementOperations;
import org.springframework.amqp.rabbit.core.RabbitManagementTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerConfig {

	@Autowired
	private ServerInfo server;


	@Autowired
	private ICaptorApiProperty iCaptorApiProperty;


	public ServerInfo getServer() {
		return server;
	}


	@Bean
	public AmqpManagementOperations amqpManagementOperations(){
		String uri = String.format("http://%s:%d/api/", iCaptorApiProperty.getBroker().getHost(), iCaptorApiProperty.getBroker().getPort());
		return new RabbitManagementTemplate(uri, iCaptorApiProperty.getBroker().getUser(), iCaptorApiProperty.getBroker().getPass());
	}

}