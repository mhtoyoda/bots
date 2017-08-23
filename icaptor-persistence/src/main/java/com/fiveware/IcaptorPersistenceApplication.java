package com.fiveware;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@EntityScan(basePackageClasses = { IcaptorPersistenceApplication.class, Jsr310JpaConverters.class })
@SpringBootApplication
public class IcaptorPersistenceApplication {

	public static void main(String[] args) {
		SpringApplication.run(IcaptorPersistenceApplication.class, args);
	}
}
