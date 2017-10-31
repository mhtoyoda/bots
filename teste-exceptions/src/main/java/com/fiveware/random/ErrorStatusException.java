package com.fiveware.random;

import static com.fiveware.automate.BotAutomationBuilder.Web;
import static com.fiveware.automate.BotWebBrowser.PHANTOM;

import com.fiveware.exception.RecoverableException;
import com.fiveware.exception.RuntimeBotException;
import com.fiveware.exception.UnRecoverableException;

public class ErrorStatusException implements ErrorRandom {

	@Override
	public void throwError() throws RecoverableException, UnRecoverableException, RuntimeBotException {
		try{
			String baseUrl = "https://www.google.com:81/";			
			Web().driver(PHANTOM).openPage(baseUrl);
			throw new RecoverableException("Erro status");
		}catch (Exception e) {
			throw new RuntimeBotException(e);
		}
	}
}