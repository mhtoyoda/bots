package com.fiveware.automate;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Frame {

	private WebDriver webDriver;

	protected Frame(WebDriver webDriver) {
		this.webDriver = webDriver;
	}

	public BotElement id(String id) {
		WebDriver frameDriver = webDriver.switchTo().frame(webDriver.findElement(By.id(id)));
		return new BotElement(frameDriver.findElement(By.id(id)));
	}

	public BotElement className(String className) {
		WebDriver frameDriver = webDriver.switchTo().frame(webDriver.findElement(By.className(className)));
		return new BotElement(frameDriver.findElement(By.className(className)));
	}

	public BotElement cssSelector(String cssSelector) {
		WebDriver frameDriver = webDriver.switchTo().frame(webDriver.findElement(By.cssSelector(cssSelector)));
		return new BotElement(frameDriver.findElement(By.cssSelector(cssSelector)));
	}

	public BotElement linkText(String linkText) {
		WebDriver frameDriver = webDriver.switchTo().frame(webDriver.findElement(By.linkText(linkText)));
		return new BotElement(frameDriver.findElement(By.linkText(linkText)));
	}

	public BotElement name(String name) {
		WebDriver frameDriver = webDriver.switchTo().frame(webDriver.findElement(By.name(name)));
		return new BotElement(frameDriver.findElement(By.name(name)));
	}

	public BotElement tagName(String tagName) {
		WebDriver frameDriver = webDriver.switchTo().frame(webDriver.findElement(By.tagName(tagName)));
		return new BotElement(frameDriver.findElement(By.tagName(tagName)));
	}

	public BotElement xpath(String xpathExpression) {
		WebDriver frameDriver = webDriver.switchTo().frame(webDriver.findElement(By.xpath(xpathExpression)));
		return new BotElement(frameDriver.findElement(By.xpath(xpathExpression)));
	}
}
