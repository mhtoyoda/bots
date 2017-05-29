package com.fiveware;

import com.fiveware.annotation.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by valdisnei on 5/28/17.
 */
@Icaptor(classloader = "com.fiveware.TesteBot", description = "Bot para consulta de ceps, serviço do Correio", value = "consultaCEP", version = "1.0.0")
public class TesteBot implements Automation<String, Endereco>{

    static Logger  logger = LoggerFactory.getLogger(TesteBot.class);

    public static void main(String[] args) {
        System.out.println(new TesteBot().getEndereco(args[0]));
    }

    public Endereco getEndereco(String args){

        WebDriver driver =setupPhantomJS();
        String baseUrl = "http://www.correios.com.br/";

        driver.get(baseUrl + "/para-voce");

        driver.manage().window().maximize();//workaround
        driver.findElement(By.id("acesso-busca")).clear();
        driver.findElement(By.id("acesso-busca")).sendKeys(args);
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


        logger.info(" endereco: {}",logradouro+" - "+bairro+" - "+localidade+" - "+cep);


        return new Endereco(logradouro,bairro,localidade,cep);
    }


    private WebDriver setupPhantomJS() {
        WebDriver driver=null;

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setJavascriptEnabled(true);
        caps.setCapability("takesScreenshot", true);

        List<String> cliArgsCap = Arrays.asList("--webdriver=34.205.146.238:8910");
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, cliArgsCap);

        try {
            driver = new RemoteWebDriver(new URL("http://34.205.146.238:8910"), caps);
            driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        } catch (MalformedURLException e) {
           // logger.error("ocorreu um problema no RemoteWebDriver: {} ", e);
        }
        return driver;
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

    @IcaptorMethod(value = "execute")
	@InputDictionary(fields = {"cep"}, separator = ",", typeFileIn = "csv")
	@OutputDictionary(fields = {"logradouro", "bairro", "localidade", "cep"}, nameFileOut= "/home/fiveware/Documentos/saida.txt", separator = "|", typeFileOut = "csv")
    public Endereco execute(@Field(length=9, regexValidate = "\\d{5}\\-?\\d{3}") String cep) {
        try {
            Endereco endereco = getEndereco(cep);

            return endereco;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }
}
