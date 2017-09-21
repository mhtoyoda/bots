package com.fiveware;

import com.fiveware.config.ICaptorApiProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.client.RestTemplate;

import java.time.format.DateTimeFormatter;

@SpringBootApplication
public class IcaptorServiceRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(IcaptorServiceRestApplication.class, args);
	}


	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}

	@Bean
	public ICaptorApiProperty iCaptorApiProperty(){
		return new ICaptorApiProperty();
	}



}
