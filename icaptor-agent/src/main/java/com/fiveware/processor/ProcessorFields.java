package com.fiveware.processor;

import com.fiveware.model.MessageBot;
import org.springframework.context.MessageSource;

import com.fiveware.model.Record;
import com.fiveware.service.IServiceBot;
import com.fiveware.validate.Validate;

import java.io.Serializable;

public class ProcessorFields {

	private final String botName;
	private final Class classLoader;
	private final IServiceBot serviceBot;
	private final Record record;
	private final Validate validate;
	private final MessageSource messageSource;
	private final MessageBot messageBot;

	public ProcessorFields(String botName, Class classLoader, IServiceBot serviceBot, Record record,
						   Validate validate, MessageSource messageSource, MessageBot messageBot) {
		this.botName = botName;
		this.classLoader = classLoader;
		this.serviceBot = serviceBot;
		this.record = record;
		this.validate = validate;
		this.messageSource = messageSource;
		this.messageBot = messageBot;
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

	public MessageBot getMessageBot() {
		return messageBot;
	}


	public static class ProcessorFieldsBuilder implements Serializable{
		private String botName;
		private Class classLoader;
		private IServiceBot serviceBot;
		private Record record;
		private Validate validate;
		private MessageSource messageSource;
		private MessageBot messageBot;


	}

}