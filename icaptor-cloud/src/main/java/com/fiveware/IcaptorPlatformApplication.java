package com.fiveware;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpManagementOperations;
import org.springframework.amqp.rabbit.core.RabbitManagementTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.fiveware.messaging.BrokerConfig;
import com.fiveware.task.TaskResolver;

@SpringBootApplication
public class IcaptorPlatformApplication implements CommandLineRunner {
	
	static final Logger logger = LoggerFactory.getLogger(IcaptorPlatformApplication.class);
	
	@Autowired
	private TaskResolver taskResolver;
	
	public static void main(String[] args) {
		SpringApplication.run(IcaptorPlatformApplication.class, args);
	}
	
	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
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

	@Override
	public void run(String... arg0) throws Exception {
		while(true){
			Thread.sleep(20000);
			logger.info("Check Status Task and ItemTask");
			taskResolver.process();
		}
	}
}
