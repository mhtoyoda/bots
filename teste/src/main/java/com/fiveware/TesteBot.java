package com.fiveware;

import com.fiveware.annotation.*;
import com.fiveware.automate.BotJS;
import com.fiveware.automate.BotWebBrowser;
import com.fiveware.automate.BotWebDriver;
import com.fiveware.exception.ExceptionBot;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

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

		return endereco;
	}

	public Endereco getEndereco(String args) {
		BotWebDriver botWebDriver = new BotWebDriver();
		botWebDriver.initialize(BotWebBrowser.PHANTOM);
		
		String baseUrl = "http://www.correios.com.br/";

		botWebDriver.openPageBrowser(baseUrl + "/para-voce");		
		botWebDriver.manage().window().maximize();
		botWebDriver.findElement(By.id("acesso-busca")).clear();
		botWebDriver.findElement(By.id("acesso-busca")).sendKeys(args);
		botWebDriver.findElement(By.cssSelector("input.acesso-busca-submit")).click();

		new BotJS(botWebDriver).waitForPageToBeReady();

		Iterator<String> windows = botWebDriver.getWindowHandles().iterator();
		windows.next();

		botWebDriver.switchTo().window(windows.next());

		String resultado = botWebDriver
				.findElement(By.xpath("/html/body/div[1]/div[3]/div[2]/div/div/div[2]/div[2]/div[2]/p")).getText();

		if ("DADOS NAO ENCONTRADOS".equalsIgnoreCase(resultado))
			return null;

		String logradouro = botWebDriver
				.findElement(By
						.xpath("/html/body/div[1]/div[3]/div[2]/div/div/div[2]/div[2]/div[2]/table/tbody/tr[2]/td[1]"))
				.getText();
		String bairro = botWebDriver
				.findElement(By
						.xpath("/html/body/div[1]/div[3]/div[2]/div/div/div[2]/div[2]/div[2]/table/tbody/tr[2]/td[2]"))
				.getText();
		String localidade = botWebDriver
				.findElement(By
						.xpath("/html/body/div[1]/div[3]/div[2]/div/div/div[2]/div[2]/div[2]/table/tbody/tr[2]/td[3]"))
				.getText();
		String cep = botWebDriver
				.findElement(By
						.xpath("/html/body/div[1]/div[3]/div[2]/div/div/div[2]/div[2]/div[2]/table/tbody/tr[2]/td[4]"))
				.getText();

		logger.info(" Endereco: {} - {} - {} - {}", logradouro, bairro, localidade, cep);

		return new Endereco(logradouro, bairro, localidade, cep);
	}
}
