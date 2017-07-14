package com.fiveware.processor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.fiveware.exception.AttributeLoadException;
import com.fiveware.exception.ExceptionBot;
import com.fiveware.loader.ClassLoaderConfig;
import com.fiveware.loader.ClassLoaderRunner;
import com.fiveware.messaging.Producer;
import com.fiveware.messaging.QueueName;
import com.fiveware.model.BotClassLoaderContext;
import com.fiveware.model.InputDictionaryContext;
import com.fiveware.model.OutTextRecord;
import com.fiveware.service.IServiceBot;
import com.fiveware.task.TaskMessageBot;
import com.fiveware.validate.Validate;

@Component("processBotFile")
public class ProcessBotFile implements ProcessBot<TaskMessageBot> {

	Logger logger = LoggerFactory.getLogger(ProcessBotFile.class);

	@Autowired
	@Qualifier("batch")
	private IServiceBot serviceBot;

	@Autowired
	@Qualifier("fieldValidate")
	private Validate validate;

	@Autowired
	private ClassLoaderConfig classLoaderConfig;

	@Autowired
	private ClassLoaderRunner classLoaderRunner;

	@Autowired
	@Qualifier("taskMessageProducer")
	private Producer<TaskMessageBot> producer;
		
	@Autowired
	private MessageSource messageSource;

	@SuppressWarnings("rawtypes")
	public void execute(String botName, TaskMessageBot obj)
			throws IOException, AttributeLoadException, ClassNotFoundException, ExceptionBot {

		logger.info("Init Import File - [BOT]: {}", botName);

		Optional<BotClassLoaderContext> context = classLoaderConfig.getPropertiesBot(botName);
		InputDictionaryContext inputDictionary = context.get().getInputDictionary();		
		Class classLoader = classLoaderRunner.loadClass(botName);

//		for (Record line : recordLines) {
//
//			OutTextRecord result = null;
//
//			Object cep = line.getValue("cep");
//
//			try {
//				validate.validate(cep, classLoader);
//
//				OutTextRecord outTextRecord = serviceBot.callBot(botName, cep);
//
//				result =  Arrays.asList(outTextRecord.getMap()).get(0)==null?getNotFound(cep).get():outTextRecord;
//
//			} catch (Exception e) {
//				logger.error("Unprocessed Record - Cause: " + e.getMessage());
//			} finally {
//				listJoin.joinRecord(separatorInput, result, listResults);
//			}
//		}
		
		producer.send(QueueName.TASKS.name(), obj);
		logger.info("End Import File - [BOT]: {}", botName);
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