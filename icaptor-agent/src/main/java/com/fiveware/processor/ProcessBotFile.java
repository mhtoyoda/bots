package com.fiveware.processor;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fiveware.exception.AttributeLoadException;
import com.fiveware.exception.ExceptionBot;
import com.fiveware.messaging.Producer;
import com.fiveware.model.OutTextRecord;
import com.fiveware.service.IServiceBot;
import com.fiveware.task.TaskMessageBot;

@Component("processBotFile")
public class ProcessBotFile implements ProcessBot<TaskMessageBot> {

	Logger logger = LoggerFactory.getLogger(ProcessBotFile.class);

	@Autowired
	@Qualifier("batch")
	private IServiceBot serviceBot;

	@Autowired
	@Qualifier("taskMessageProducer")
	private Producer<TaskMessageBot> producer;

	public void execute(String botName, TaskMessageBot obj)
			throws IOException, AttributeLoadException, ClassNotFoundException, ExceptionBot {

		logger.info("Init Import File - [BOT]: {}", botName);
		try{
			OutTextRecord outTextRecord = serviceBot.callBot(botName, null);
		} catch (Exception e) {
			logger.error("Unprocessed Record - Cause: " + e.getMessage());
		}
		
		producer.send(botName+"_OUT", obj);
		logger.info("End Import File - [BOT]: {}", botName);
	}
}