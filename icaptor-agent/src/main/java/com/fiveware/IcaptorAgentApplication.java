package com.fiveware;

import org.springframework.amqp.core.AmqpManagementOperations;
import org.springframework.amqp.rabbit.core.RabbitManagementTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

import com.fiveware.messaging.BrokerConfig;

@SpringBootApplication
@PropertySource(ignoreResourceNotFound=true, value="classpath:icaptor-platform.properties")
public class IcaptorAgentApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(IcaptorAgentApplication.class, args);	
	}

	@Bean
	public BrokerConfig brokerConfig(){
		return new BrokerConfig();
	}
	
	@Bean
	public AmqpManagementOperations amqpManagementOperations(){
		String uri = String.format("http://%s:%d/api/", brokerConfig().getHost(), brokerConfig().getPort());
		return new RabbitManagementTemplate(uri, brokerConfig().getUser(), brokerConfig().getPass());
	}
}