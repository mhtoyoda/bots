package com.fiveware;

import com.fiveware.bot.ConsultaCEP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

//@SpringBootApplication
public class CorreioApplication implements CommandLineRunner {

	static Logger logger = LoggerFactory.getLogger(CorreioApplication.class);

	@Autowired
	private ConsultaCEP consultaCEP;

	public static void main(String[] args) {
		SpringApplication.run(CorreioApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		logger.info(consultaCEP.getEndereco(strings[0]).toString());
	}
	


}
