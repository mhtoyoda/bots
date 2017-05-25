package com.fiveware;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.fiveware.loader.LoadFile;

@SpringBootApplication(scanBasePackages = {"com.fiveware", "com.correios"})
public class AgentIcaptorApplication {
	
	private final static Logger log = LoggerFactory.getLogger(AgentIcaptorApplication.class);
	
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(AgentIcaptorApplication.class, args);
		try {
			context.getBean(LoadFile.class).executeLoad(new File(""));
		} catch (Exception e) {
			log.error("Erro : "+e.getMessage());
		}
	}


}
