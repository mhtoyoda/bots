package com.fiveware.random;

import static com.fiveware.automate.BotAutomationBuilder.Web;
import static com.fiveware.automate.BotWebBrowser.PHANTOM;

import com.fiveware.exception.ExceptionBot;

public class ErrorStatusException implements ErrorRandom {

	@Override
	public void throwError() throws ExceptionBot {
		String baseUrl = "https://www.google.com:81/";
		Web().driver(PHANTOM).openPage(baseUrl);	
	}
}