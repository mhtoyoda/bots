package com.fiveware.processor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;

import com.fiveware.exception.AttributeLoadException;
import com.fiveware.exception.ExceptionBot;
import com.fiveware.exception.ValidationFieldException;
import com.fiveware.model.OutTextRecord;
import com.fiveware.model.Record;
import com.fiveware.service.IServiceBot;
import com.fiveware.validate.Validate;

public class ProcessorRunnable implements Callable<OutTextRecord> {
	
	Logger logger = LoggerFactory.getLogger(ProcessorRunnable.class);
	
	private String botName;
	private Class classLoader;
	private IServiceBot serviceBot;
	private Record record;
	private Validate validate;
	private MessageSource messageSource;
	
	public ProcessorRunnable(String botName, Class classLoader, IServiceBot serviceBot, Record record,
			Validate validate, MessageSource messageSource) {
		this.botName = botName;
		this.classLoader = classLoader;
		this.serviceBot = serviceBot;
		this.record = record;
		this.validate = validate;
		this.messageSource = messageSource;
	}

	@Override
	public OutTextRecord call() throws Exception {
		OutTextRecord result = null;
		try {
			result = getResult();
		} catch (ValidationFieldException | AttributeLoadException | ExceptionBot e) {
			logger.error("Unprocessed Record - Cause: " + e.getMessage());
		}		
		return result;
	}
	
	private OutTextRecord getResult() throws ValidationFieldException, AttributeLoadException, ExceptionBot{
		Object cep = record.getValue("cep");
		validate.validate(cep, classLoader);
		OutTextRecord outTextRecord = serviceBot.callBot(botName, cep);
		return Arrays.asList(outTextRecord.getMap()).get(0)==null?getNotFound(cep).get():outTextRecord;
	}
	
	private Supplier<OutTextRecord> getNotFound(Object parameter) {
		Supplier<OutTextRecord> supplier = new Supplier<OutTextRecord>() {
			@Override
			public OutTextRecord get() {
				String message = messageSource.getMessage("result.bot.notFound", new Object[]{parameter}, null);
				HashMap<String, Object> notFound = new HashMap<>();
				notFound.put("registro", message);
				return new OutTextRecord(new HashMap[]{notFound});
			}
		};
		return supplier;
	}
}