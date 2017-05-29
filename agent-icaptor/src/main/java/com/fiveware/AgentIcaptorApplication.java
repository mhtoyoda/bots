package com.fiveware;

import com.fiveware.loader.LoadFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.File;


@SpringBootApplication
public class AgentIcaptorApplication{
	
	private final static Logger log = LoggerFactory.getLogger(AgentIcaptorApplication.class);
	
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(AgentIcaptorApplication.class, args);
		try {
			context.getBean(LoadFile.class).executeLoad(new File("/home/fiveware/Documentos/cep.txt"));
		} catch (Exception e) {
			log.error("Erro : "+e.getMessage());
		}
	}
}
