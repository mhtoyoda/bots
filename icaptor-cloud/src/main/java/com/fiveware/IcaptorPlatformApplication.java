package com.fiveware;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.fiveware.register.ServerRegister;

@SpringBootApplication
public class IcaptorPlatformApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(IcaptorPlatformApplication.class, args);
		applicationContext.getBean(ServerRegister.class).register();
	}
}
