package com.fiveware.loader;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fiveware.exception.AttributeLoadException;
import com.fiveware.exception.ExceptionBot;
import com.fiveware.file.FileUtil;
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
		List<String> results = Lists.newArrayList();
		for (Record line : recordLines) {
			try {
				String cep = (String) line.getValue("cep");
				validate.validate(cep, classLoader);
				OutTextRecord result = serviceBot.callBot(botName, cep);
				listJoin.joinRecord(separatorInput, result, results);				
			} catch (Exception e) {
				logger.error("Unprocessed Record - Cause: " + e.getMessage());				
			}
		}
		obj.getLineResult().addAll(results);
		producer.send(botName+"_OUT", obj);		
		logger.info("End Import File - [BOT]: {}", botName);
	}

}