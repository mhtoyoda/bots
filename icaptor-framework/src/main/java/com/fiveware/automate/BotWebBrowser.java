package com.fiveware.automate;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

public enum BotWebBrowser {

	FIREFOX {
		@Override
		public WebDriver getDriver() {
			return new FirefoxDriver();
		}
	}, 
	CHROME {
		@Override
		public WebDriver getDriver() {
			return new ChromeDriver();
		}
	}, 
	SAFARI {
		@Override
		public WebDriver getDriver() {
			return new SafariDriver();
		}
	},		
	PHANTOM {
		@Override
		public WebDriver getDriver() {
			WebDriver driver = null;
			DesiredCapabilities caps = new DesiredCapabilities();
	        caps.setJavascriptEnabled(true);
	        caps.setCapability("takesScreenshot", true);

	        List<String> cliArgsCap = Arrays.asList("--webdriver=" + "34.206.50.158:8910");
	        caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, cliArgsCap);

	        try {
	            driver = new RemoteWebDriver(new URL("http://34.206.50.158:8910"), caps);
	            driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	        } catch (MalformedURLException e) {
	            driver = new PhantomJSDriver();
	        }
	        return driver;
		}
	};
	
	public abstract WebDriver getDriver();
}
