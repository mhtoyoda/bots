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
import com.fiveware.exception.RuntimeBotException;
import com.fiveware.loader.ClassLoaderConfig;
import com.fiveware.loader.ClassLoaderRunner;
import com.fiveware.messaging.Producer;
import com.fiveware.model.BotClassLoaderContext;
import com.fiveware.model.InputDictionaryContext;
import com.fiveware.model.OutTextRecord;
import com.fiveware.model.Record;
import com.fiveware.model.message.MessageBot;
import com.fiveware.service.IServiceBach;
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
	private IServiceBach serviceBach;

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
			throws IOException, AttributeLoadException, ClassNotFoundException, RuntimeBotException {

		logger.info("Init Import File - [BOT]: {}", botName);

		Optional<BotClassLoaderContext> context = classLoaderConfig.getPropertiesBot(botName);
		InputDictionaryContext inputDictionary = context.get().getInputDictionary();
		String separatorInput = inputDictionary.getSeparator();
		String[] fieldsInput = inputDictionary.getFields();
		String line = obj.getLine();
		Record record = lineUtil.linesFrom(line, fieldsInput, separatorInput);
		Class classLoader = classLoaderRunner.loadClass(botName);

		ExecutorService executorService = Executors.newFixedThreadPool(2);

			try {
				ProcessorFields processorFields = new ProcessorFields.ProcessorFieldsBuilder()
															.nameBot(botName)
															.classLoader(classLoader)
															.bot(serviceBach)
															.record(record)
															.validate(validate)
															.messageSource(messageSource)
															.messageBot(obj)
															.context(context)
															.build();
				Future<OutTextRecord> outTextRecord = executorService.submit(new ProcessorRunnable(processorFields));

				List<String> resultSuccess = Lists.newArrayList();
				listJoin.joinRecord(separatorInput, outTextRecord.get(), resultSuccess);

			} catch (Exception e) {
				logger.error("Unprocessed Record - Cause: " + e.getMessage());
				if (e.getCause() instanceof RuntimeBotException) {
					throw new RuntimeBotException(e.getMessage());
				}
			}

		producer.send("task.out", obj);
		executorService.shutdown();
		logger.info("End Import File - [BOT]: {}", botName);
	}

}