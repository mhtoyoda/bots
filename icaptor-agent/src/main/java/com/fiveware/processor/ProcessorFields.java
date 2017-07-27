package com.fiveware.processor;

import org.springframework.context.MessageSource;

import com.fiveware.model.Record;
import com.fiveware.service.IServiceBot;
import com.fiveware.validate.Validate;

public class ProcessorFields {

	private String botName;
	private Class classLoader;
	private IServiceBot serviceBot;
	private Record record;
	private Validate validate;
	private MessageSource messageSource;

	public ProcessorFields(String botName, Class classLoader, IServiceBot serviceBot, Record record, Validate validate,
			MessageSource messageSource) {
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
}