package com.fiveware.automate;

import org.openqa.selenium.WebElement;

public class BotElement {

	private WebElement webElement;
	
	protected BotElement(WebElement webElement) {
		this.webElement = webElement;
	}

	protected WebElement geWebElement(){
		return webElement;
	}
	
	public BotElement clear() {
		webElement.clear();
		return this;
	}
	
	public BotElement click() {
		webElement.click();
		return this;
	}

	public String getAttribute(String argument) {
		return webElement.getAttribute(argument);
	}

	public String getCssValue(String argument) {
		return webElement.getCssValue(argument);
	}

	public int getHeight() {
		return webElement.getSize().getHeight();
	}

	public int getWidth() {
		return webElement.getSize().getWidth();
	}

	public String getTagName() {
		return webElement.getTagName();
	}

	public String getText() {
		return webElement.getText();
	}

	public boolean isDisplayed() {
		return webElement.isDisplayed();
	}

	public boolean isEnabled() {
		return webElement.isEnabled();
	}

	public boolean isSelected() {
		return webElement.isSelected();
	}

	public void sendKeys(CharSequence... args) {
		webElement.clear();
		webElement.sendKeys(args);
	}

	public void submit() {
		webElement.submit();
	}
}