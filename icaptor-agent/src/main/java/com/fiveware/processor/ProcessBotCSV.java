package com.fiveware.processor;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
import com.fiveware.model.BotClassLoaderContext;
import com.fiveware.model.InputDictionaryContext;
import com.fiveware.model.MessageBot;
import com.fiveware.model.OutTextRecord;
import com.fiveware.model.Record;
import com.fiveware.service.IServiceBot;
import com.fiveware.util.LineUtil;
import com.fiveware.util.ListJoinUtil;
import com.fiveware.validate.Validate;
import com.google.common.collect.Lists;

@Component("processBotCSV")
public class ProcessBotCSV implements ProcessBot<MessageBot> {

	Logger logger = LoggerFactory.getLogger(ProcessBotCSV.class);

	@Autowired
	private LineUtil lineUtil;

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
	@Qualifier("eventBotProducer")
	private Producer<MessageBot> producer;

	@Autowired
	private ListJoinUtil listJoin;
		
	@Autowired
	private MessageSource messageSource;

	@SuppressWarnings("rawtypes")
	public void execute(String botName, MessageBot obj)
			throws IOException, AttributeLoadException, ClassNotFoundException, ExceptionBot {

		logger.info("Init Import File - [BOT]: {}", botName);

		Optional<BotClassLoaderContext> context = classLoaderConfig.getPropertiesBot(botName);
		InputDictionaryContext inputDictionary = context.get().getInputDictionary();
		String separatorInput = inputDictionary.getSeparator();
		String[] fieldsInput = inputDictionary.getFields();

		List<Record> recordLines = lineUtil.linesFrom(obj.getLine(), fieldsInput, separatorInput);
		Class classLoader = classLoaderRunner.loadClass(botName);
		List<String> listResults = Lists.newArrayList();
		
		ExecutorService executorService = Executors.newFixedThreadPool(obj.getQtdeInstances());
		for (Record line : recordLines) {
			OutTextRecord result = null;
			try {
				ProcessorFields processorFields = new ProcessorFields(botName, classLoader, serviceBot, line, validate, messageSource);
				Future<OutTextRecord> outTextRecord = executorService.submit(new ProcessorRunnable(processorFields));
				result = outTextRecord.get();
			} catch (Exception e) {
				logger.error("Unprocessed Record - Cause: " + e.getMessage());
			} finally {
				listJoin.joinRecord(separatorInput, result, listResults);
			}
		}
		
		producer.send(botName + "_OUT", obj);
		executorService.shutdown();
		logger.info("End Import File - [BOT]: {}", botName);
	}
}