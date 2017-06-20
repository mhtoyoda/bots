package com.fiveware.loader;

import java.io.IOException;
import java.util.*;
import java.util.function.Supplier;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Maps;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.fiveware.exception.AttributeLoadException;
import com.fiveware.exception.ExceptionBot;
import com.fiveware.util.FileUtil;
import com.fiveware.messaging.Producer;
import com.fiveware.model.*;
import com.fiveware.service.ServiceBot;
import com.fiveware.util.ListJoinUtil;
import com.fiveware.validate.Validate;
import com.google.common.collect.Lists;

@Component
public class ProcessBotLoader {

	Logger logger = LoggerFactory.getLogger(ProcessBotLoader.class);

	@Autowired
	private FileUtil fileUtil;

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
	private Supplier<? extends Map<String, Object>[]> notFound;

	@SuppressWarnings("rawtypes")
	public void executeLoad(String botName, MessageBot obj)
			throws IOException, AttributeLoadException, ClassNotFoundException, ExceptionBot {

		logger.info("Init Import File - [BOT]: {}", botName);

		Optional<BotClassLoaderContext> context = classLoaderConfig.getPropertiesBot(botName);
		InputDictionaryContext inputDictionary = context.get().getInputDictionary();
		String separatorInput = inputDictionary.getSeparator();
		String[] fieldsInput = inputDictionary.getFields();

		List<Record> recordLines = fileUtil.linesFrom(obj.getLine(), fieldsInput, separatorInput);
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

	public Supplier<OutTextRecord> getNotFound(Object parameter) {
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