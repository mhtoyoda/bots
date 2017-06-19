package com.fiveware;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.fiveware.register.ServerRegister;

@SpringBootApplication
public class IcaptorPlatformApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(IcaptorPlatformApplication.class, args);
		applicationContext.getBean(ServerRegister.class).register();
	}
	
	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
}
