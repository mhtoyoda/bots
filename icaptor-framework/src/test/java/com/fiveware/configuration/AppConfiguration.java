package com.fiveware.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fiveware.validate.FieldValidate;
import com.fiveware.validate.ObjectValidate;
import com.fiveware.validate.Validate;
import com.fiveware.validate.ValidateMethodField;

@Configuration
@EnableAutoConfiguration
public class AppConfiguration {

	@Bean
	public Validate objectValidate(){
		Validate objectValidate = new ObjectValidate();
		return objectValidate;
	}
	
	@Bean
	public Validate fieldValidate(){
		Validate fieldValidate = new FieldValidate();
		return fieldValidate;
	}
	
	@Bean
	public ValidateMethodField validateMethodField(){
		ValidateMethodField validateMethodField = new ValidateMethodField();
		return validateMethodField;
	}
}
