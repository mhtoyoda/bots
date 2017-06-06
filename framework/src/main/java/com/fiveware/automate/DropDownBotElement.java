package com.fiveware.automate;


import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.google.common.collect.Lists;

public class DropDownBotElement {
	
	private Select select;
	
	public DropDownBotElement(BotElement botElement) {		
		this.select = new Select(botElement.geWebElement());
	}

	public void deselectAll() {
		select.deselectAll();
	}

	public void deselectByIndex(int arg0) {
		select.deselectByIndex(arg0);
	}

	public void deselectByValue(String arg0) {
		select.deselectByValue(arg0);
	}

	public void deselectByVisibleText(String arg0) {
		select.deselectByVisibleText(arg0);
	}

	public List<BotElement> getAllSelectedOptions() {
		List<BotElement> list = Lists.newArrayList();
		select.getAllSelectedOptions().forEach(element -> {
			list.add(new BotElement(element));
		});
		return list;
	}

	public BotElement getFirstSelectedOption() {
		WebElement webElement = select.getFirstSelectedOption();
		return new BotElement(webElement);
	}

	public List<BotElement> getOptions() {
		List<BotElement> list = Lists.newArrayList();
		select.getOptions().forEach(option -> {
			list.add(new BotElement(option));
		});
		return list;
	}

	public boolean isMultiple() {
		return select.isMultiple();
	}

	public void selectByIndex(int arg0) {
		select.selectByIndex(arg0);
	}

	public void selectByValue(String arg0) {
		select.selectByValue(arg0);
	}

	public void selectByVisibleText(String arg0) {
		select.selectByVisibleText(arg0);
	}	
}