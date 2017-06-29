package com.fiveware.automate;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.google.common.collect.Lists;

public class Elements {

	private WebDriver webDriver;

	protected Elements(WebDriver webDriver) {
		this.webDriver = webDriver;
	}

	public List<BotElement> id(String id) {
		List<BotElement> list = Lists.newArrayList();		
		List<WebElement> elements = webDriver.findElements(By.id(id));
		elements.forEach(element -> {
			list.add(new BotElement(element));
		});
		return list;
	}

	public List<BotElement> className(String className) {
		List<BotElement> list = Lists.newArrayList();		
		List<WebElement> elements = webDriver.findElements(By.className(className));
		elements.forEach(element -> {
			list.add(new BotElement(element));
		});
		return list;		
	}

	public List<BotElement> cssSelector(String cssSelector) {
		List<BotElement> list = Lists.newArrayList();		
		List<WebElement> elements = webDriver.findElements(By.cssSelector(cssSelector));
		elements.forEach(element -> {
			list.add(new BotElement(element));
		});
		return list;	
	}
	
	public List<BotElement> linkText(String linkText) {
		List<BotElement> list = Lists.newArrayList();		
		List<WebElement> elements = webDriver.findElements(By.linkText(linkText));
		elements.forEach(element -> {
			list.add(new BotElement(element));
		});
		return list;	
	}

	public List<BotElement> name(String name) {
		List<BotElement> list = Lists.newArrayList();		
		List<WebElement> elements = webDriver.findElements(By.name(name));
		elements.forEach(element -> {
			list.add(new BotElement(element));
		});
		return list;	
	}

	public List<BotElement> tagName(String tagName) {
		List<BotElement> list = Lists.newArrayList();		
		List<WebElement> elements = webDriver.findElements(By.tagName(tagName));
		elements.forEach(element -> {
			list.add(new BotElement(element));
		});
		return list;	
	}

	public List<BotElement> xpath(String xpathExpression) {
		List<BotElement> list = Lists.newArrayList();		
		List<WebElement> elements = webDriver.findElements(By.xpath(xpathExpression));
		elements.forEach(element -> {
			list.add(new BotElement(element));
		});
		return list;	
	}
}
