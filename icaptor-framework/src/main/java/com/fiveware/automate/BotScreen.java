package com.fiveware.automate;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.WebDriver.TargetLocator;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BotScreen {

	private WebDriver webDriver;

	public BotScreen driver(BotWebBrowser webBrowser) {
		if (null == webDriver) {
			webDriver = webBrowser.getDriver();
		}
		return this;
	}

	public BotScreen openPage(String url) {
		webDriver.get(url);
		return this;
	}

	public void closeOpenBrowser() {
		webDriver.close();
	}

	public String getCurrentUrl() {
		return webDriver.getCurrentUrl();
	}

	public String getPageSource() {
		return webDriver.getPageSource();
	}

	public String getTitle() {
		return webDriver.getTitle();
	}

	public String getWindowHandle() {
		return webDriver.getWindowHandle();
	}

	public Iterator<String> iteratorWindowHandles() {
		return webDriver.getWindowHandles().iterator();
	}

	private Options manage() {
		return webDriver.manage();
	}

	public BotScreen windowMaximize() {
		manage().window().maximize();
		return this;
	}

	public BotScreen fullScreen() {
		manage().window().fullscreen();
		return this;
	}

	public BotScreen navigate(String url) {
		webDriver.navigate().to(url);
		return this;
	}

	public void quit() {
		webDriver.quit();
	}

	private TargetLocator switchTo() {
		return webDriver.switchTo();
	}

	public BotScreen window(String next) {
		switchTo().window(next);
		return this;
	}
	
	public BotAutomation find(){
		return new BotAutomation(webDriver);
	}
	
	public BotScreen waitForPageToLoadFor(int time){
		new BotJS(webDriver).waitForPageToLoadFor(time);
		return this;
	}	

	public BotScreen waitVisibilityOfElementByCss(int time, BotScreen botScreen, String cssSelector) {
		Wait<WebDriver> wait = new WebDriverWait(webDriver, time);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(cssSelector)));
		return this;
	}
	
	public BotScreen waitVisibilityOfElementById(int time, BotScreen botScreen, String id) {
		Wait<WebDriver> wait = new WebDriverWait(webDriver, time);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));
		return this;
	}
	
	public BotScreen wait(int time) {		
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return this;
	}
			
	public BotScreen waitImplicit(int time) {
		webDriver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
		return this;
	}
	
	public BotScreen doFireEvent(String event, BotElement botElement){
		new BotJS(webDriver).doFireEvent(event, botElement);
		return this;
	}
}