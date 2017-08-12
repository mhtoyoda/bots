package com.fiveware.processor;

import com.fiveware.exception.AttributeLoadException;
import com.fiveware.exception.ExceptionBot;
import com.fiveware.exception.UnRecoverableException;
import com.fiveware.exception.ValidationFieldException;
import com.fiveware.model.OutTextRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

public class ProcessorRunnable implements Callable<OutTextRecord> {
	
	Logger logger = LoggerFactory.getLogger(ProcessorRunnable.class);
	
	private ProcessorFields processorFields;
	
	public ProcessorRunnable(ProcessorFields processorFields) {
		this.processorFields = processorFields;		
	}

	@Override
	public OutTextRecord call() throws ExceptionBot,UnRecoverableException {
		OutTextRecord result = null;
		result = getResult();		
		return result;
	}
	
	private OutTextRecord getResult() throws ExceptionBot {
		Object cep = processorFields.getRecord().getValue("cep");
		try {
			processorFields.getValidate().validate(cep, processorFields.getClassLoader());
		} catch (ValidationFieldException | AttributeLoadException e) {
			logger.error("Unprocessed Record - Cause: " + e.getMessage());
			return getErrorValidation(cep).get();
		}
		OutTextRecord outTextRecord = processorFields.getServiceBatch().callBot(processorFields, cep);
		return Arrays.asList(outTextRecord.getMap()).get(0)==null?getNotFound(cep).get():outTextRecord;
	}
	
	private Supplier<OutTextRecord> getNotFound(Object parameter) {
		Supplier<OutTextRecord> supplier = new Supplier<OutTextRecord>() {
			@Override
			public OutTextRecord get() {
				String message = processorFields.getMessageSource().getMessage("result.bot.notFound", new Object[]{parameter}, null);
				HashMap<String, Object> notFound = new HashMap<>();
				notFound.put("registro", message);
				return new OutTextRecord(new HashMap[]{notFound});
			}
		};
		return supplier;
	}
	
	private Supplier<OutTextRecord> getErrorValidation(Object parameter) {
		Supplier<OutTextRecord> supplier = new Supplier<OutTextRecord>() {
			@Override
			public OutTextRecord get() {
				String message = processorFields.getMessageSource().getMessage("result.bot.validation", new Object[]{parameter}, null);
				HashMap<String, Object> notFound = new HashMap<>();
				notFound.put("registro", message);
				return new OutTextRecord(new HashMap[]{notFound});
			}
		};
		return supplier;
	}
}