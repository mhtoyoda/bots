package com.fiveware.validate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fiveware.configuration.AppConfiguration;
import com.fiveware.exception.AttributeLoadException;
import com.fiveware.exception.ValidationFieldException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfiguration.class})
@ComponentScan(basePackages = {"com.fiveware.validate"})
public class FieldConfigTest {

	@Autowired
	@Qualifier("fieldValidate")
	private Validate fieldValidate;
	
	@Autowired
	@Qualifier("objectValidate")
	private Validate objectValidate;

	@Test
	public void objectValidateTest() throws ValidationFieldException, AttributeLoadException {
		Endereco endereco = new Endereco();	
		endereco.setCep("01310-100");
		endereco.setLogradouro("Avenida Paulista");
		endereco.setNumero(20);
		objectValidate.validate(endereco, BotTesteEndereco.class);
	}
	
	@Test
	public void fieldValidateTest() throws ValidationFieldException, AttributeLoadException {
		String cep = "01310-100";		
		fieldValidate.validate(cep, BotTeste.class);
	}
}
