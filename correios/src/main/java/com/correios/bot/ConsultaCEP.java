package com.correios.bot;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.correios.domain.Endereco;
import com.fiveware.Automation;
import com.fiveware.annotation.Bot;
import com.fiveware.annotation.InputDictionary;
import com.fiveware.file.RecordLine;

@Component
@Bot
public class ConsultaCEP implements Automation{

    static Logger logger = LoggerFactory.getLogger(ConsultaCEP.class);

    private WebDriver driver;
    private String baseUrl;

    public Endereco getEndereco(String cepPesquisa) throws Exception {

        setupPhantomJS();

        driver.get(baseUrl + "/para-voce");

        driver.manage().window().maximize();//workaround
        driver.findElement(By.id("acesso-busca")).clear();
        driver.findElement(By.id("acesso-busca")).sendKeys(cepPesquisa);
        driver.findElement(By.cssSelector("input.acesso-busca-submit")).click();

        waitForPageToBeReady(driver);

        Iterator<String> windows = driver.getWindowHandles().iterator();
        windows.next();

        driver.switchTo().window(windows.next());

        String resultado = driver.findElement(By.xpath("/html/body/div[1]/div[3]/div[2]/div/div/div[2]/div[2]/div[2]/p")).getText();

        if ("DADOS NAO ENCONTRADOS".equalsIgnoreCase(resultado))
            return null;

        String logradouro = driver.findElement(By.xpath("/html/body/div[1]/div[3]/div[2]/div/div/div[2]/div[2]/div[2]/table/tbody/tr[2]/td[1]")).getText();
        String bairro = driver.findElement(By.xpath("/html/body/div[1]/div[3]/div[2]/div/div/div[2]/div[2]/div[2]/table/tbody/tr[2]/td[2]")).getText();
        String localidade = driver.findElement(By.xpath("/html/body/div[1]/div[3]/div[2]/div/div/div[2]/div[2]/div[2]/table/tbody/tr[2]/td[3]")).getText();
        String cep = driver.findElement(By.xpath("/html/body/div[1]/div[3]/div[2]/div/div/div[2]/div[2]/div[2]/table/tbody/tr[2]/td[4]")).getText();

        return new Endereco(logradouro, bairro, localidade, cep);
    }


    private void setupPhantomJS() {

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setJavascriptEnabled(true);
        caps.setCapability("takesScreenshot", true);

        List<String> cliArgsCap = Arrays.asList("--webdriver=54.224.242.0:8910");
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, cliArgsCap);

        try {
            driver = new RemoteWebDriver(new URL("http://54.224.242.0:8910"), caps);
        } catch (MalformedURLException e) {
            logger.error("ocorreu um problema no RemoteWebDriver: {} ", e);
        }

        baseUrl = "http://www.correios.com.br/";
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

    }

    private static void waitForPageToBeReady(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        //This loop will rotate for 100 times to check If page Is ready after every 1 second.
        //You can replace your if you wants to Increase or decrease wait time.
        for (int i = 0; i < 400; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }

            //To check page ready state.
            if ("complete".equals(js.executeScript("return document.readyState").toString()))
                break;
        }
    }


	@Override
	@InputDictionary(fields = {"cep"}, separator = ",", typeFileIn = "csv")
	public RecordLine execute(RecordLine recordLine) {
		try {
			getEndereco(recordLine.getRecordMap().get("cep").toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}

