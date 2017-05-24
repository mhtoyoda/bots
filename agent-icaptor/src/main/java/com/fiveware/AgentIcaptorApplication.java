package com.fiveware;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AgentIcaptorApplication implements CommandLineRunner {

	@Autowired
	private Automation automation;

	public static void main(String[] args) {
		SpringApplication.run(AgentIcaptorApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		automation.execute(strings);
	}
}
