package com.fiveware;

import com.fiveware.config.ICaptorApiProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpManagementOperations;
import org.springframework.amqp.rabbit.core.RabbitManagementTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class IcaptorAgentApplication {
	static Logger log = LoggerFactory.getLogger(IcaptorAgentApplication.class);

	private final Environment env;

	public IcaptorAgentApplication(Environment env) {
		this.env = env;
	}

	@LocalServerPort
	private int port;


	public static void main(String[] args) throws UnknownHostException {
		SpringApplication.run(IcaptorAgentApplication.class,args);

	}


	@Autowired
	private ICaptorApiProperty iCaptorApiProperty;
	
	@Bean
	public AmqpManagementOperations amqpManagementOperations(){
		String uri = String.format("http://%s:%d/api/", iCaptorApiProperty.getBroker().getHost(), iCaptorApiProperty.getBroker().getPort());
		return new RabbitManagementTemplate(uri, iCaptorApiProperty.getBroker().getUser(), iCaptorApiProperty.getBroker().getPass());
	}
}