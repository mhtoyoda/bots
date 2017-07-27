package com.fiveware.processor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fiveware.exception.AttributeLoadException;
import com.fiveware.exception.ExceptionBot;
import com.fiveware.exception.ValidationFieldException;
import com.fiveware.model.OutTextRecord;

public class ProcessorRunnable implements Callable<OutTextRecord> {
	
	Logger logger = LoggerFactory.getLogger(ProcessorRunnable.class);
	
	private ProcessorFields processorFields;
	
	public ProcessorRunnable(ProcessorFields processorFields) {
		this.processorFields = processorFields;		
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
		Object cep = processorFields.getRecord().getValue("cep");
		processorFields.getValidate().validate(cep, processorFields.getClassLoader());
		OutTextRecord outTextRecord = processorFields.getServiceBot().callBot(processorFields.getBotName(), cep);
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
}