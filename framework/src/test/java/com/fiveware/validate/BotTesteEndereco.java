package com.fiveware.validate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fiveware.Automation;
import com.fiveware.annotation.Icaptor;
import com.fiveware.annotation.IcaptorMethod;
import com.fiveware.annotation.InputDictionary;
import com.fiveware.annotation.OutputDictionary;

@Icaptor(value = "testeCEP", classloader = "com.fiveware.BotTeste", 
		 description = "Bot para Testes", version = "1.0.0")
public class BotTesteEndereco implements Automation<Endereco, com.fiveware.validate.Endereco> {

	static Logger logger = LoggerFactory.getLogger(BotTesteEndereco.class);

	@IcaptorMethod(value = "execute", endpoint = "bot")
	@InputDictionary(fields = { "cep" }, separator = ",", typeFileIn = "csv")
	@OutputDictionary(fields = { "logradouro", "bairro", "localidade",
			"cep" }, nameFileOut = "saida.txt", separator = "|", typeFileOut = "csv")
	public Endereco execute(Endereco endereco) {
		try {
			endereco.setCep("01310-100");
			endereco.setLogradouro("Avenida Paulista");
			endereco.setNumero(20);
			logger.debug(endereco.toString());
			return endereco;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}
}