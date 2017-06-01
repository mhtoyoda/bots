package com.fiveware;

import java.util.Iterator;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fiveware.annotation.*;
import com.fiveware.file.PhantomJSHelper;

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

	@IcaptorMethod(value = "execute", endpoint = "correios-bot")
	@InputDictionary(fields = {"cep"}, separator = ",", typeFileIn = "csv")
	@OutputDictionary(fields = {"logradouro", "bairro", "localidade",
			"cep"}, nameFileOut = "saida.txt", separator = "|", typeFileOut = "csv")
	public Endereco execute(@Field(name = "cep", length = 9, regexValidate = "\\d{5}\\-?\\d{3}") String cep) {
		try {
			Endereco endereco = getEndereco(cep);

			return endereco;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	public Endereco getEndereco(String args) {

		WebDriver driver = PhantomJSHelper.remotePhantomJS("34.206.50.158:8910");
		String baseUrl = "http://www.correios.com.br/";

		driver.get(baseUrl + "/para-voce");

		driver.manage().window().maximize();// workaround
		driver.findElement(By.id("acesso-busca")).clear();
		driver.findElement(By.id("acesso-busca")).sendKeys(args);
		driver.findElement(By.cssSelector("input.acesso-busca-submit")).click();

		waitForPageToBeReady(driver);

		Iterator<String> windows = driver.getWindowHandles().iterator();
		windows.next();

		driver.switchTo().window(windows.next());

		String resultado = driver
				.findElement(By.xpath("/html/body/div[1]/div[3]/div[2]/div/div/div[2]/div[2]/div[2]/p")).getText();

		if ("DADOS NAO ENCONTRADOS".equalsIgnoreCase(resultado))
			return null;

		String logradouro = driver
				.findElement(By
						.xpath("/html/body/div[1]/div[3]/div[2]/div/div/div[2]/div[2]/div[2]/table/tbody/tr[2]/td[1]"))
				.getText();
		String bairro = driver
				.findElement(By
						.xpath("/html/body/div[1]/div[3]/div[2]/div/div/div[2]/div[2]/div[2]/table/tbody/tr[2]/td[2]"))
				.getText();
		String localidade = driver
				.findElement(By
						.xpath("/html/body/div[1]/div[3]/div[2]/div/div/div[2]/div[2]/div[2]/table/tbody/tr[2]/td[3]"))
				.getText();
		String cep = driver
				.findElement(By
						.xpath("/html/body/div[1]/div[3]/div[2]/div/div/div[2]/div[2]/div[2]/table/tbody/tr[2]/td[4]"))
				.getText();

		logger.info(" Endereco: {} - {} - {} - {}", logradouro, bairro, localidade, cep);

		return new Endereco(logradouro, bairro, localidade, cep);
	}

	private static void waitForPageToBeReady(WebDriver driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver;

		// This loop will rotate for 100 times to check If page Is ready after
		// every 1 second.
		// You can replace your if you wants to Increase or decrease wait time.
		for (int i = 0; i < 400; i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}

			// To check page ready state.
			if ("complete".equals(js.executeScript("return document.readyState").toString()))
				break;
		}
	}
}
