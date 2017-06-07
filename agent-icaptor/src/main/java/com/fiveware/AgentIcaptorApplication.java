package com.fiveware;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fiveware.messaging.Producer;
import com.fiveware.messaging.Receiver;
import com.fiveware.messaging.TypeMessage;

@SpringBootApplication
public class AgentIcaptorApplication implements CommandLineRunner {
	
	private final static Logger log = LoggerFactory.getLogger(AgentIcaptorApplication.class);
	
	@Autowired
	private Producer<String> producer;
	
	@Autowired
	private Receiver<String> receiver;
	
	public static void main(String[] args) {
		SpringApplication.run(AgentIcaptorApplication.class, args);		
	}

	@Override
	public void run(String... args) throws Exception {
		producer.send(TypeMessage.KEEP_ALIVE, "TESTE 12345");
		log.info("SEND");
		
		Thread.sleep(5000);
		receiver.receive(TypeMessage.KEEP_ALIVE);
	}


}