package com.fiveware;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class AgentIcaptorApplication{
	
//	private final static Logger log = LoggerFactory.getLogger(AgentIcaptorApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(AgentIcaptorApplication.class, args);
//		try {
//			context.getBean(LoadFile.class).executeLoad(new File("/home/fiveware/Documentos/cep.txt"));
//		} catch (Exception e) {
//			log.error("Erro : "+e.getMessage());
//		}
	}
}