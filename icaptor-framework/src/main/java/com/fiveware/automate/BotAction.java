package com.fiveware.automate;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class BotAction {
	
	private Actions actions;
	
	private BotElement botElement;
	
	protected BotAction(WebDriver driver, BotElement botElement) {
		this.actions = new Actions(driver);
		this.botElement = botElement;
	}
	
	public void doubleClick() {
		WebElement element = botElement.geWebElement();
		actions.moveToElement(element).doubleClick().build().perform();
	}
	
	public void scroll(){
		WebElement element = botElement.geWebElement();
		actions.moveToElement(element).clickAndHold().moveByOffset(0,50).release().perform();
	}
}