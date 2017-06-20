package com.fiveware.automate;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.WebDriver.TargetLocator;
import org.openqa.selenium.WebElement;

import com.google.common.collect.Lists;

public class BotWebDriver {
	
	private WebDriver webDriver;
	
	public void initialize(BotWebBrowser webBrowser){
		if( null == webDriver ){
			webDriver = webBrowser.getDriver();			
		}
	}
		
	protected WebDriver getWebDriver() {
		return webDriver;
	}

	public void openPageBrowser(String url){
		webDriver.get(url);
	}
	
	public void closeOpenBrowser() {
		webDriver.close();
	}

	public BotElement findElement(By by) {
		return new BotElement(webDriver.findElement(by));
	}

	public List<BotElement> findElements(By by) {
		List<BotElement> list = Lists.newArrayList();		
		List<WebElement> elements = webDriver.findElements(by);
		elements.forEach(element -> {
			list.add(new BotElement(element));
		});
		return list;
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

	public Set<String> getWindowHandles() {
		return webDriver.getWindowHandles();
	}

	public Options manage() {
		return webDriver.manage();
	}

	public Navigation navigate() {
		return webDriver.navigate();
	}

	public void quit() {
		webDriver.quit();
	}

	public TargetLocator switchTo() {
		return webDriver.switchTo();
	}

}
