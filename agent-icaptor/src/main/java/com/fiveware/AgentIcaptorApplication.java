package com.fiveware;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fiveware.messaging.Producer;
import com.fiveware.messaging.Receiver;

@SpringBootApplication
public class AgentIcaptorApplication implements CommandLineRunner {
	
	private final static Logger log = LoggerFactory.getLogger(AgentIcaptorApplication.class);
	
	@Autowired
	private Producer producer;
	
	@Autowired
	private Receiver receiver;
	
	public static void main(String[] args) {
		SpringApplication.run(AgentIcaptorApplication.class, args);		
	}

	@Override
	public void run(String... args) throws Exception {
		producer.send("TESTE 12345");
		log.info("SEND");
		
		Thread.sleep(5000);
		receiver.receive("");
	}


}