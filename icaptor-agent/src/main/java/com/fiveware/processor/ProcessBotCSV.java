package com.fiveware.processor;

import com.fiveware.config.agent.AgentConfigProperties;
import com.fiveware.context.QueueContext;
import com.fiveware.exception.AttributeLoadException;
import com.fiveware.exception.ParameterInvalidException;
import com.fiveware.exception.RuntimeBotException;
import com.fiveware.loader.ClassLoaderConfig;
import com.fiveware.loader.ClassLoaderRunner;
import com.fiveware.loader.ParameterClassLoader;
import com.fiveware.messaging.Producer;
import com.fiveware.messaging.QueueName;
import com.fiveware.messaging.Receiver;
import com.fiveware.messaging.TypeMessage;
import com.fiveware.model.BotClassLoaderContext;
import com.fiveware.model.InputDictionaryContext;
import com.fiveware.model.OutTextRecord;
import com.fiveware.model.Record;
import com.fiveware.model.message.MessageAgent;
import com.fiveware.model.message.MessageBot;
import com.fiveware.model.message.MessageParameterAgent;
import com.fiveware.model.message.MessageTaskAgent;
import com.fiveware.parameter.ParameterResolver;
import com.fiveware.parameter.ParameterValue;
import com.fiveware.service.IServiceBach;
import com.fiveware.util.LineUtil;
import com.fiveware.util.ListJoinUtil;
import com.fiveware.validate.Validate;
import com.google.common.collect.Lists;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Component("processBotCSV")
public class ProcessBotCSV implements ProcessBot<MessageBot> {

	Logger logger = LoggerFactory.getLogger(ProcessBotCSV.class);

	@Autowired
    private AgentConfigProperties data;
	
	@Autowired
	private LineUtil lineUtil;

	@Autowired
	private IServiceBach serviceBach;

	@Autowired
	private ClassLoaderConfig classLoaderConfig;

	@Autowired
	private ClassLoaderRunner classLoaderRunner;

	@Autowired
	@Qualifier("eventBotProducer")
	private Producer<MessageBot> producer;
	
	@Autowired
	@Qualifier("eventMessageProducer")
	private Producer<MessageAgent> eventProducer;
	
	@Autowired
	@Qualifier("parameterMessageReceiver")
	private Receiver<MessageParameterAgent> eventReceiver;
	
	@Autowired
	private ListJoinUtil listJoin;
		
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private ParameterResolver parameterResolver;
	
	@Autowired
	private QueueContext queueContext;
	
	@Autowired
	private ParameterClassLoader parameterClassLoader;
	
	@SuppressWarnings("rawtypes")
	public void execute(String botName, MessageBot messageBot)
			throws IOException, AttributeLoadException, ClassNotFoundException, RuntimeBotException, ParameterInvalidException {

		logger.info("Init Import File - [BOT]: {}", botName);
		Optional<BotClassLoaderContext> context = classLoaderConfig.getPropertiesBot(botName);
		ParameterValue parameterValue =	getParameters(botName, messageBot.getTaskId());
		InputDictionaryContext inputDictionary = context.get().getInputDictionary();
		String separatorInput = inputDictionary.getSeparator();
		String[] fieldsInput = inputDictionary.getFields();
		String line = messageBot.getLine();
		Record record = lineUtil.linesFrom(line, fieldsInput, separatorInput);
		Class classLoader = classLoaderRunner.loadClass(botName);
		sendNotificationItemTaskProcessing(messageBot.getTaskId(), messageBot.getItemTaskId());

		ExecutorService executorService = Executors.newFixedThreadPool(2);
			try {
				Validate validate = parameterClassLoader.getValidateByType(context.get());
				ProcessorFields processorFields = new ProcessorFields.ProcessorFieldsBuilder()
															.nameBot(botName)
															.classLoader(classLoader)
															.bot(serviceBach)
															.record(record)
															.validate(validate)
															.messageSource(messageSource)
															.messageBot(messageBot)
															.context(context)
															.parameter(parameterValue)
															.build();
				
				Future<OutTextRecord> outTextRecord = executorService.submit(new ProcessorRunnable(processorFields, parameterClassLoader));
				List<String> resultSuccess = Lists.newArrayList();
				listJoin.joinRecord(separatorInput, outTextRecord.get(), resultSuccess);
			} catch (Exception e) {
				logger.error("Unprocessed Record - Cause: " + e.getMessage());
				if (e.getCause() instanceof RuntimeBotException) {
					throw new RuntimeBotException(e.getMessage());
				}
			}finally {
				executorService.shutdown();
				try {
					executorService.awaitTermination(60, TimeUnit.SECONDS);
				} catch (InterruptedException e) {					
					e.printStackTrace();
				}
			}
		
		messageBot.setNameAgent(data.getAgentName());
		messageBot.setBotName(botName);
		producer.send("task.out", messageBot);
		logger.info("End Import File - [BOT]: {}", botName);
	}

	private ParameterValue getParameters(String botName, Long taskId) throws ParameterInvalidException {
		ParameterValue parameterValue = null;
		if(parameterResolver.hasNecessaryParameterFromBot(botName)){		
			MessageParameterAgent messageParameterAgent = waitParameterMessage(botName, data.getAgentName(), taskId);
			if(null != messageParameterAgent){
				parameterValue = new ParameterValue();
				List<String> fieldValues = messageParameterAgent.getFieldValue();
				for(String field: fieldValues){
					String[] values = field.split(":");
					if(values[0].toString().equals("credential") || values[0].toString().equals("login")){
						String decode = new String(Base64.decodeBase64(values[2].toString()));
						String[] value = decode.split(":");
						parameterValue.add(values[1].toString(), value[0], value[1]);
					}
					else{
						String[] valuesParameters = field.split("\\|");
						if(valuesParameters.length == 4){
							parameterValue.add(valuesParameters[1].toString(), valuesParameters[2].toString(), valuesParameters[3].toString());
						}else if(valuesParameters.length == 3){
							parameterValue.add(valuesParameters[1].toString(), valuesParameters[1].toString(), valuesParameters[2].toString());
						}												
					}
				}
			}
		}
		return parameterValue;
	}
	
	private void sendNotificationItemTaskProcessing(Long taskId, Long itemTaskId){
		MessageTaskAgent message = new MessageTaskAgent(taskId, itemTaskId);
		message.setTypeMessage(TypeMessage.ITEM_TASK_PROCESSING);
		eventProducer.send(QueueName.EVENTS.name(), message);
	}

	private MessageParameterAgent waitParameterMessage(String botName, String nameAgent, Long taskId) throws ParameterInvalidException {	
		MessageParameterAgent message = new MessageParameterAgent(nameAgent, botName, null, null, true);
		message.setTypeMessage(TypeMessage.PARAMETERS);
		eventProducer.send(QueueName.EVENTS.name(), message );
		boolean notReceivedMessage = true;
		MessageParameterAgent parameter = null;			
		while(notReceivedMessage){				
			parameter = eventReceiver.receive("parameter."+nameAgent);				
			if(null != parameter){					
				notReceivedMessage = false;				
			}
		}
		
		if(CollectionUtils.isEmpty(parameter.getFieldValue())){	
			String queueName = String.format("%s.%s.IN", botName, taskId);
			queueContext.removeQueueInContext(botName, nameAgent, queueName);
			throw new ParameterInvalidException();					
		}else{						
			return parameter;					
		}
	}
}