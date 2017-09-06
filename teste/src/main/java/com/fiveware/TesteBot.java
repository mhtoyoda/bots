package com.fiveware;

import static com.fiveware.automate.BotAutomationBuilder.Web;
import static com.fiveware.automate.BotWebBrowser.PHANTOM;

import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fiveware.annotation.Icaptor;
import com.fiveware.annotation.IcaptorMethod;
import com.fiveware.annotation.IcaptorParameter;
import com.fiveware.annotation.InputDictionary;
import com.fiveware.annotation.OutputDictionary;
import com.fiveware.automate.BotScreen;
import com.fiveware.exception.AuthenticationBotException;
import com.fiveware.exception.RecoverableException;
import com.fiveware.exception.RuntimeBotException;
import com.fiveware.exception.UnRecoverableException;
import com.fiveware.parameter.ParameterValue;

/**
 * Created by valdisnei on 5/28/17.
 */

@Icaptor(value = "consultaCEP", classloader = "com.fiveware.TesteBot",
		description = "Bot para consulta de ceps, serviço do Correio",version = "1.0.0")
public class TesteBot implements Automation<Endereco, Endereco> {

	static Logger logger = LoggerFactory.getLogger(TesteBot.class);
	
	public static void main(String[] args) throws RuntimeBotException, UnRecoverableException, RecoverableException, AuthenticationBotException {
		Endereco endereco = new TesteBot().getEndereco("07077170");
		logger.info("Resultado: {}", endereco);
	}
	
	@IcaptorParameter(value = "maria:12345", nameTypeParameter = "login", exclusive = true, credential = true)
	@IcaptorParameter(value = "joao:12345", nameTypeParameter = "login", exclusive = true, credential = true)
	@IcaptorParameter(value = "10", regexValidate = "[0-9]", nameTypeParameter = "timeout", exclusive = false, credential = false)
	@IcaptorParameter(value = "1", regexValidate = "[0-9]{1}", nameTypeParameter = "retry", exclusive = false,  credential = false)
	@IcaptorMethod(value = "execute", endpoint = "correios-bot", type = Endereco.class)
	@InputDictionary(fields = {"cep"}, separator = "|	", typeFileIn = "csv")
	@OutputDictionary(fields = {"logradouro", "bairro", "localidade", "cep"},
					  nameFileOut = "saida.txt", separator = "|", typeFileOut = "csv")
	public Endereco execute(Endereco endereco, ParameterValue parameters) throws RuntimeBotException,UnRecoverableException,RecoverableException, AuthenticationBotException {
		
//		throw new AuthenticationBotException("Simulando Exception Authentication");
//		throw new RuntimeBotException("Simulando bug ");

//		throw new RecoverableException("Simulando bug RecoverableException");
		logger.info("Dados de Endereco: {}",endereco.toString());
		logger.info("Dados de Parametros: {}s",parameters.toString());
		return getEndereco(endereco.getCep());
	}

	public Endereco getEndereco(String args) throws RuntimeBotException, UnRecoverableException, RecoverableException {
		String baseUrl = "http://www.correios.com/";
		BotScreen telaConsultaCep = Web().driver(PHANTOM).openPage(baseUrl + "/para-voce");
		telaConsultaCep.windowMaximize();
		telaConsultaCep.find().elementBy().id("acesso-busca").sendKeys(args);		
		telaConsultaCep.find().elementBy().cssSelector("input.acesso-busca-submit").click();
		
		telaConsultaCep.waitForPageToLoadFor(400);

		Iterator<String> windows = telaConsultaCep.iteratorWindowHandles();
		windows.next();

		telaConsultaCep.window(windows.next());

		String resultado = telaConsultaCep.find()
				.elementBy().xpath("/html/body/div[1]/div[3]/div[2]/div/div/div[2]/div[2]/div[2]/p").getText();

		if ("DADOS NAO ENCONTRADOS".equalsIgnoreCase(resultado)){
			logger.warn("DADOS NAO ENCONTRADOS");
			throw new RecoverableException("DADOS NAO ENCONTRADOS");
		}

		String logradouro = telaConsultaCep.find()
				.elementBy().xpath("/html/body/div[1]/div[3]/div[2]/div/div/div[2]/div[2]/div[2]/table/tbody/tr[2]/td[1]")
				.getText();
		String bairro = telaConsultaCep.find()
				.elementBy().xpath("/html/body/div[1]/div[3]/div[2]/div/div/div[2]/div[2]/div[2]/table/tbody/tr[2]/td[2]")
				.getText();
		String localidade = telaConsultaCep.find()
				.elementBy().xpath("/html/body/div[1]/div[3]/div[2]/div/div/div[2]/div[2]/div[2]/table/tbody/tr[2]/td[3]")
				.getText();
		String cep = telaConsultaCep.find()
				.elementBy().xpath("/html/body/div[1]/div[3]/div[2]/div/div/div[2]/div[2]/div[2]/table/tbody/tr[2]/td[4]")
				.getText();

		logger.info(" Endereco: {} - {} - {} - {}", logradouro, bairro, localidade, cep);

		return new Endereco(logradouro, bairro, localidade, cep);
	}
}
