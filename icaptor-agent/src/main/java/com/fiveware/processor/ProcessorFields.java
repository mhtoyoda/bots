package com.fiveware.processor;

import com.fiveware.model.BotClassLoaderContext;
import com.fiveware.model.MessageBot;
import com.fiveware.model.Record;
import com.fiveware.service.IServiceBot;
import com.fiveware.validate.Validate;
import org.springframework.context.MessageSource;

import java.io.Serializable;
import java.util.Optional;

public class ProcessorFields {

	private final String botName;
	private final Class classLoader;
	private final IServiceBot serviceBot;
	private final Record record;
	private final Validate validate;
	private final MessageSource messageSource;
	private final MessageBot messageBot;
	private final Optional<BotClassLoaderContext> context;

	public ProcessorFields(String botName, Class classLoader, IServiceBot serviceBot, Record record,
						   Validate validate, MessageSource messageSource, MessageBot messageBot,
						   Optional<BotClassLoaderContext> context) {
		this.botName = botName;
		this.classLoader = classLoader;
		this.serviceBot = serviceBot;
		this.record = record;
		this.validate = validate;
		this.messageSource = messageSource;
		this.messageBot=messageBot;
		this.context = context;
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

	public Optional<BotClassLoaderContext> getContext() {return context;	}

	public MessageBot getMessageBot() {return messageBot;	}

	public static class ProcessorFieldsBuilder implements Serializable{
		private String botName;
		private Class classLoader;
		private IServiceBot serviceBot;
		private Record record;
		private Validate validate;
		private MessageSource messageSource;
		private MessageBot messageBot;
		private Optional<BotClassLoaderContext> context;

		public ProcessorFieldsBuilder nameBot(String name){
			this.botName=name;
			return this;
		}
		public ProcessorFieldsBuilder classLoader(Class classLoader){
			this.classLoader=classLoader;
			return this;
		}
		public ProcessorFieldsBuilder bot(IServiceBot iServiceBot){
			this.serviceBot=iServiceBot;
			return this;
		}
		public ProcessorFieldsBuilder record(Record record){
			this.record=record;
			return this;
		}
		public ProcessorFieldsBuilder validate(Validate validate){
			this.validate=validate;
			return this;
		}

		public ProcessorFieldsBuilder messageSource(MessageSource messageSource){
			this.messageSource =messageSource;
			return this;
		}

		public ProcessorFieldsBuilder messageBot(MessageBot messageBot){
			this.messageBot=messageBot;
			return this;
		}

		public ProcessorFieldsBuilder context(Optional<BotClassLoaderContext> context) {
			this.context=context;
			return null;
		}

		public ProcessorFields build(){
			return new ProcessorFields(this.botName,this.classLoader,
					this.serviceBot,this.record,this.validate,
					this.messageSource,this.messageBot, context);
		}
	}

}