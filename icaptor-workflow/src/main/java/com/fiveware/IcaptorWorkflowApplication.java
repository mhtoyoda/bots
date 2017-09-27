package com.fiveware;

import com.fiveware.config.ICaptorApiProperty;
import org.springframework.amqp.core.AmqpManagementOperations;
import org.springframework.amqp.rabbit.core.RabbitManagementTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class IcaptorWorkflowApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(IcaptorWorkflowApplication.class, args);
	}

	@Autowired
	private ICaptorApiProperty iCaptorApiProperty;
	
	@Bean
	public AmqpManagementOperations amqpManagementOperations(){
		String uri = String.format("http://%s:%d/api/", iCaptorApiProperty.getBroker().getHost(), iCaptorApiProperty.getBroker().getPort());
		return new RabbitManagementTemplate(uri, iCaptorApiProperty.getBroker().getUser(), iCaptorApiProperty.getBroker().getPass());
	}
}