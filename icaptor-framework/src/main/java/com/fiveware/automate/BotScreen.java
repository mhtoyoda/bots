package com.fiveware.automate;

import java.util.Iterator;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.WebDriver.TargetLocator;

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

	public BotAutomation find() {
		return new BotAutomation(webDriver);
	}

	public BotScreen waitForPageToLoadFor(int time) {
		new BotJS(webDriver).waitForPageToLoadFor(time);
		return this;
	}
}
