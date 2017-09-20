package com.fiveware;

import com.fiveware.config.ICaptorApiProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableConfigurationProperties(ICaptorApiProperty.class)
public class IcaptorWebSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(IcaptorWebSecurityApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}


	@Bean
	public ICaptorApiProperty iCaptorApiProperty(){
		return new ICaptorApiProperty();
	}



}
