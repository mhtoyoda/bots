package com.fiveware;

import com.fiveware.task.TaskResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IcaptorMonitoringApplication implements CommandLineRunner {
	
	static final Logger logger = LoggerFactory.getLogger(IcaptorMonitoringApplication.class);
	
	@Autowired
	private TaskResolver taskResolver;


	public static void main(String[] args) {
		SpringApplication.run(IcaptorMonitoringApplication.class, args);
	}



	@Override
	public void run(String... arg0) throws Exception {
		while(true){
			Thread.sleep(5000);
			logger.info("Check Status Task and ItemTask");
			try{
				taskResolver.process();
			}catch (Exception e) {
				logger.error("Error {}", e);
			}			
		}
	}




}
