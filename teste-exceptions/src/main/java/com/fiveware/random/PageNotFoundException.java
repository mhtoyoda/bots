package com.fiveware.random;

import static com.fiveware.automate.BotAutomationBuilder.Web;
import static com.fiveware.automate.BotWebBrowser.PHANTOM;

import com.fiveware.exception.ExceptionBot;

public class PageNotFoundException implements ErrorRandom {

	@Override
	public void throwError() throws ExceptionBot {
		String baseUrl = "https://xpto2222222.com.br";
		Web().driver(PHANTOM).openPage(baseUrl);
	}
}