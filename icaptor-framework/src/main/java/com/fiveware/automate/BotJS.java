package com.fiveware.automate;

import org.openqa.selenium.JavascriptExecutor;

public class BotJS {
	
	private BotWebDriver driver;
	
	public BotJS(BotWebDriver driver) {
		this.driver = driver;
	}

	public void waitForPageToBeReady() {
		JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
		for (int i = 0; i < 400; i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			
			if ("complete".equals(js.executeScript("return document.readyState").toString()))
				break;
		}
	}
}
