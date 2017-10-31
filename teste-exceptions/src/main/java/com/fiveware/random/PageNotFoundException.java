package com.fiveware.random;

import static com.fiveware.automate.BotAutomationBuilder.Web;
import static com.fiveware.automate.BotWebBrowser.PHANTOM;
import com.fiveware.exception.RecoverableException;
import com.fiveware.exception.RuntimeBotException;
import com.fiveware.exception.UnRecoverableException;

public class PageNotFoundException implements ErrorRandom {

	@Override
	public void throwError() throws RecoverableException, UnRecoverableException, RuntimeBotException {
		String baseUrl = "https://xpto2222222.com.br";
		Web().driver(PHANTOM).openPage(baseUrl);		
	}

}