package com.fiveware.automate;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import com.thoughtworks.selenium.webdriven.JavascriptLibrary;

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
	
	protected void doFireEvent(String event, BotElement botElement) {
		JavascriptLibrary javascriptLibrary = new JavascriptLibrary();
		javascriptLibrary.callEmbeddedSelenium(driver, "doFireEvent", botElement.geWebElement(), event);
	}
	
	public void scrollUp() {
		JavascriptExecutor js = (JavascriptExecutor) driver;	
		js.executeScript("window.scrollBy(0,-300)", "");
	}
	
	public void scrollDown(int num) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,"+num+")", "");
	}
}