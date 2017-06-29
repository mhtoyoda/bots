package com.fiveware.automate;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Element {

	private WebDriver webDriver;

	protected Element(WebDriver webDriver) {
		this.webDriver = webDriver;
	}

	public BotElement id(String id) {
		return new BotElement(webDriver.findElement(By.id(id)));
	}

	public BotElement className(String className) {
		return new BotElement(webDriver.findElement(By.className(className)));
	}

	public BotElement cssSelector(String cssSelector) {
		return new BotElement(webDriver.findElement(By.cssSelector(cssSelector)));
	}
	
	public BotElement linkText(String linkText) {
		return new BotElement(webDriver.findElement(By.linkText(linkText)));
	}

	public BotElement name(String name) {
		return new BotElement(webDriver.findElement(By.name(name)));
	}

	public BotElement tagName(String tagName) {
		return new BotElement(webDriver.findElement(By.tagName(tagName)));
	}

	public BotElement xpath(String xpathExpression) {
		return new BotElement(webDriver.findElement(By.xpath(xpathExpression)));
	}
}
