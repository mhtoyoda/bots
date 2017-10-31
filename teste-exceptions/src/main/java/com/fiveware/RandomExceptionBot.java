package com.fiveware;

import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fiveware.Automation;
import com.fiveware.annotation.Field;
import com.fiveware.annotation.Icaptor;
import com.fiveware.annotation.IcaptorMethod;
import com.fiveware.annotation.InputDictionary;
import com.fiveware.annotation.OutputDictionary;
import com.fiveware.exception.AuthenticationBotException;
import com.fiveware.exception.RecoverableException;
import com.fiveware.exception.RuntimeBotException;
import com.fiveware.exception.UnRecoverableException;
import com.fiveware.parameter.ParameterValue;
import com.fiveware.random.ErrorRandom;
import com.fiveware.random.ErrorStatusException;
import com.fiveware.random.LayoutBrokenException;
import com.fiveware.random.PageNotFoundException;
import com.google.common.collect.Lists;

@Icaptor(value = "randomExceptions", classloader = "com.fiveware.RandomExceptionBot", 
		 description = "Bot para simular exceptions",version = "1.0.0")
public class RandomExceptionBot implements Automation<String, String> {

	static Logger logger = LoggerFactory.getLogger(RandomExceptionBot.class);
	
	private List<ErrorRandom> list = Lists.newArrayList(new LayoutBrokenException(), new ErrorStatusException(), new PageNotFoundException());
	
	public static void main(String[] args) {		
		try {
			new RandomExceptionBot().execute("12345-000", null);
		} catch (RuntimeBotException e) {
			logger.error("[RuntimeBotException] "+e.getMessage());
			
		} catch (UnRecoverableException e) {
			logger.error("[UnRecoverableException] "+e.getMessage());
		
		} catch (RecoverableException e) {
			logger.error("[RecoverableException] "+e.getMessage());	
		
		} catch (AuthenticationBotException e) {
			logger.error("[AuthenticationBotException] "+e.getMessage());
		
		}		
	}

	@IcaptorMethod(value = "execute", endpoint = "random-bot",type = String.class)
	@InputDictionary(fields = {"cep"}, separator = ",", typeFileIn = "csv")
	@OutputDictionary(fields = {"logradouro", "bairro", "localidade","cep"},
					  nameFileOut = "saida.txt", separator = "|", typeFileOut = "csv")
	public String execute(@Field(name = "cep", length = 9) String cep, ParameterValue parameters) throws RuntimeBotException, UnRecoverableException, RecoverableException, AuthenticationBotException{
		Random random = new Random();
		int index = random.nextInt(list.size());
		list.get(index).throwError();
		return "OK";
	}
}