package com.fiveware;

import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fiveware.annotation.Field;
import com.fiveware.annotation.Icaptor;
import com.fiveware.annotation.IcaptorMethod;
import com.fiveware.annotation.InputDictionary;
import com.fiveware.annotation.OutputDictionary;
import com.fiveware.exception.ExceptionBot;
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
			new RandomExceptionBot().execute("12345-000");
		} catch (ExceptionBot e) {
			logger.error("[Erro] "+e.getMessage());
		} catch (Exception e){
			logger.error("[RuntimeException] "+e.getMessage());
		}
	}

	@IcaptorMethod(value = "execute", endpoint = "random-bot",type = String.class)
	@InputDictionary(fields = {"cep"}, separator = ",", typeFileIn = "csv")
	@OutputDictionary(fields = {"logradouro", "bairro", "localidade","cep"},
					  nameFileOut = "saida.txt", separator = "|", typeFileOut = "csv")
	public String execute(@Field(name = "cep", length = 9) String cep) throws ExceptionBot{
		Random random = new Random();
		int index = random.nextInt(list.size());
		list.get(index).throwError();
		return "OK";
	}
	
	
}