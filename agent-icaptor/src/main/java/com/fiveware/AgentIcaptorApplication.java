package com.fiveware;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AgentIcaptorApplication{
	
	private final static Logger log = LoggerFactory.getLogger(AgentIcaptorApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(AgentIcaptorApplication.class, args);
//		try {
//			ServiceBot serviceBot = context.getBean(ServiceBot.class);
//			Class classLoader = serviceBot.loadClassLoader();
//			context.getBean(LoadFile.class).executeLoad(classLoader, new File("/home/fiveware/Documentos/cep.txt"));
//		} catch (Exception e) {
//			log.error("Erro : "+e.getMessage());
//		}
	}
}