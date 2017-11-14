package com.fiveware.processor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fiveware.exception.AttributeLoadException;
import com.fiveware.exception.RuntimeBotException;
import com.fiveware.exception.UnRecoverableException;
import com.fiveware.exception.ValidationFieldException;
import com.fiveware.loader.ParameterClassLoader;
import com.fiveware.model.OutTextRecord;

public class ProcessorRunnable implements Callable<OutTextRecord> {
	
	Logger logger = LoggerFactory.getLogger(ProcessorRunnable.class);
	
	private ProcessorFields processorFields;

	private ParameterClassLoader parameterClassLoader;
	
	public ProcessorRunnable(ProcessorFields processorFields, ParameterClassLoader parameterClassLoader) {
		this.processorFields = processorFields;
		this.parameterClassLoader = parameterClassLoader;
	}

	@Override
	public OutTextRecord call() throws RuntimeBotException,UnRecoverableException {
		OutTextRecord result = null;
		result = getResult();		
		return result;
	}
	
	private OutTextRecord getResult() throws RuntimeBotException {
		Object parameter = null;
		try {
			parameter = parameterClassLoader.loaderParameter(processorFields.getContext().get(), processorFields.getRecord());
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException | InstantiationException e1) {
			logger.error("Error Loader Parameters - Cause: " + e1.getMessage());
			throw new RuntimeBotException(e1.getMessage());
		}
		try {
			processorFields.getValidate().validate(parameter, processorFields.getClassLoader());
		} catch (ValidationFieldException | AttributeLoadException e) {
			logger.error("Unprocessed Record - Cause: " + e.getMessage());
			return getErrorValidation(parameter).get();
		}
		OutTextRecord outTextRecord = processorFields.getServiceBatch().callBot(processorFields, parameter);
		return Arrays.asList(outTextRecord.getMap()).get(0)==null?getNotFound(parameter).get():outTextRecord;
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