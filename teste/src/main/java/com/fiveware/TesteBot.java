package com.fiveware;

import com.fiveware.annotation.*;
import com.fiveware.automate.BotScreen;
import com.fiveware.dsl.TypeSearch;
import com.fiveware.exception.ExceptionBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

import static com.fiveware.automate.BotAutomationBuilder.Web;
import static com.fiveware.automate.BotWebBrowser.PHANTOM;
import static com.fiveware.dsl.Helpers.helpers;

/**
 * Created by valdisnei on 5/28/17.
 */

@Icaptor(value = "consultaCEP", classloader = "com.fiveware.TesteBot",
		description = "Bot para consulta de ceps, serviço do Correio",version = "1.0.0")
public class TesteBot implements Automation<String, Endereco> {

	static Logger logger = LoggerFactory.getLogger(TesteBot.class);
	
	public static void main(String[] args) {
		logger.info("Resultado: {}", new TesteBot().getEndereco(args[0]));
	}

	@IcaptorMethod(value = "execute", endpoint = "correios-bot",type = String.class)
	@InputDictionary(fields = {"cep"}, separator = ",", typeFileIn = "csv")
	@OutputDictionary(fields = {"logradouro", "bairro", "localidade","cep"},
					  nameFileOut = "saida.txt", separator = "|", typeFileOut = "csv")
	public Endereco execute(@Field(name = "cep", length = 9, regexValidate = "\\d{5}\\-?\\d{3}") String cep) throws ExceptionBot{
			Endereco endereco = getEndereco(cep);

		String build = helpers().pdf()
				.open("",2)
				.search("CNPJ: ", TypeSearch.CNPJ)
				.build();


		return endereco;
	}

	public Endereco getEndereco(String args) {
		String baseUrl = "http://www.correios.com.br/";
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
			return null;			
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
