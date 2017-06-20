package com.fiveware.validate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fiveware.Automation;
import com.fiveware.annotation.Field;
import com.fiveware.annotation.Icaptor;
import com.fiveware.annotation.IcaptorMethod;
import com.fiveware.annotation.InputDictionary;
import com.fiveware.annotation.OutputDictionary;

@Icaptor(value = "testeCEP", classloader = "com.fiveware.BotTeste", 
		 description = "Bot para Testes", version = "1.0.0")
public class BotTeste implements Automation<String, com.fiveware.validate.Endereco> {

	static Logger logger = LoggerFactory.getLogger(String.class);

	@IcaptorMethod(value = "execute", endpoint = "bot", type = String.class)
	@InputDictionary(fields = { "cep" }, separator = ",", typeFileIn = "csv")
	@OutputDictionary(fields = { "logradouro", "bairro", "localidade",
			"cep" }, nameFileOut = "saida.txt", separator = "|", typeFileOut = "csv")
	public Endereco execute(@Field(name = "cep", length = 9, regexValidate = "\\d{5}\\-?\\d{3}") String cep) {
		try {			
			com.fiveware.validate.Endereco endereco = new Endereco();
			logger.debug(cep);
			return endereco;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}
}