package com.fiveware.automate;

import org.openqa.selenium.WebDriver;

public class BotAutomation {

	private WebDriver webDriver;

	protected BotAutomation(WebDriver webDriver) {
		this.webDriver = webDriver;
	}

	public Element elementBy() {
		return new Element(webDriver);
	}
	
	public Elements elementsBy() {
		return new Elements(webDriver);
	}
	
	public DropDownElement dropdown(BotElement botElement){
		return new DropDownElement(botElement);
	}	
	
}
