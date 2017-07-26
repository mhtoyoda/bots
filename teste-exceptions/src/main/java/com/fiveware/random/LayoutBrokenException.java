package com.fiveware.random;

import static com.fiveware.automate.BotAutomationBuilder.Web;
import static com.fiveware.automate.BotWebBrowser.PHANTOM;

import com.fiveware.automate.BotScreen;
import com.fiveware.exception.ExceptionBot;

public class LayoutBrokenException implements ErrorRandom {

	@Override
	public void throwError() throws ExceptionBot {
		String baseUrl = "https://www.google.com.br/";
		BotScreen screen = Web().driver(PHANTOM).openPage(baseUrl);
		screen.windowMaximize();
		screen.find().elementBy().id("qualquer_coisa").sendKeys("Teste");
	}
}