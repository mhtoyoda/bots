package com.fiveware.processor;

import java.io.Serializable;

import org.springframework.context.MessageSource;

import com.fiveware.model.Record;
import com.fiveware.service.IServiceBot;
import com.fiveware.validate.Validate;

public class ProcessorFields {

	private final String botName;
	private final Class classLoader;
	private final IServiceBot serviceBot;
	private final Record record;
	private final Validate validate;
	private final MessageSource messageSource;	

	public ProcessorFields(String botName, Class classLoader, IServiceBot serviceBot, Record record,
						   Validate validate, MessageSource messageSource) {
		this.botName = botName;
		this.classLoader = classLoader;
		this.serviceBot = serviceBot;
		this.record = record;
		this.validate = validate;
		this.messageSource = messageSource;
	}

	public String getBotName() {
		return botName;
	}

	public Class getClassLoader() {
		return classLoader;
	}

	public IServiceBot getServiceBot() {
		return serviceBot;
	}

	public Record getRecord() {
		return record;
	}

	public Validate getValidate() {
		return validate;
	}

	public MessageSource getMessageSource() {
		return messageSource;
	}

	public static class ProcessorFieldsBuilder implements Serializable{
		private String botName;
		private Class classLoader;
		private IServiceBot serviceBot;
		private Record record;
		private Validate validate;
		private MessageSource messageSource;
	}

}