package com.fiveware.automate;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class BotJS {
	
	private WebDriver driver;
	
	protected BotJS(WebDriver driver) {
		this.driver = driver;
	}

	protected void waitForPageToLoadFor(int time) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		for (int i = 0; i < time; i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			
			if ("complete".equals(js.executeScript("return document.readyState").toString()))
				break;
		}
	}
}
