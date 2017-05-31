package com.fiveware.file;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by valdisnei on 31/05/17.
 */
public class PhantomJSHelper {

    static Logger logger = LoggerFactory.getLogger(PhantomJSHelper.class);


    private final static PhantomJSHelper phantomJSHelper = new PhantomJSHelper();

    private PhantomJSHelper(){
    }

    public synchronized static WebDriver remotePhantomJS(String hostPort) {
        WebDriver driver = null;

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setJavascriptEnabled(true);
        caps.setCapability("takesScreenshot", true);

        List<String> cliArgsCap = Arrays.asList("--webdriver=" + hostPort);
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, cliArgsCap);

        try {
            driver = new RemoteWebDriver(new URL("http://" + hostPort),
                    caps);
            driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        } catch (MalformedURLException e) {
            logger.error("ocorreu um problema no RemoteWebDriver: {} ", e);
        }
        return driver;
    }
}
