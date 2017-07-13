package com.fiveware.processor;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
import com.fiveware.model.BotClassLoaderContext;
import com.fiveware.model.InputDictionaryContext;
import com.fiveware.model.MessageBot;
import com.fiveware.model.OutTextRecord;
import com.fiveware.model.Record;
import com.fiveware.service.ServiceBot;
import com.fiveware.util.LineUtil;
import com.fiveware.util.ListJoinUtil;
import com.fiveware.validate.Validate;
import com.google.common.collect.Lists;

@Component("processBotDefault")
public class ProcessBotDefault implements ProcessBot {

	Logger logger = LoggerFactory.getLogger(ProcessBotDefault.class);

	@Autowired
	private LineUtil lineUtil;

	@Autowired
	@Qualifier("batch")
	private ServiceBot serviceBot;

	@Autowired
	@Qualifier("fieldValidate")
	private Validate validate;

	@Autowired
	private ClassLoaderConfig classLoaderConfig;

	@Autowired
	private ClassLoaderRunner classLoaderRunner;

	@Autowired
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
		for (Record line : recordLines) {

			OutTextRecord result = null;

			Object cep = line.getValue("cep");

			try {
				validate.validate(cep, classLoader);

				OutTextRecord outTextRecord = serviceBot.callBot(botName, cep);

				result =  Arrays.asList(outTextRecord.getMap()).get(0)==null?getNotFound(cep).get():outTextRecord;

			} catch (Exception e) {
				logger.error("Unprocessed Record - Cause: " + e.getMessage());
			} finally {
				listJoin.joinRecord(separatorInput, result, listResults);
			}
		}
		obj.getLineResult().addAll(listResults);
		producer.send(botName + "_OUT", obj);
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