package com.fiveware.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fiveware.model.AgentParameter;
import com.fiveware.model.message.MessageAgent;
import com.fiveware.model.message.MessageParameterAgent;
import com.fiveware.parameter.ParameterResolver;

@Component("PARAMETERS")
public class CloudParameterMessage implements ConsumerTypeMessage<MessageParameterAgent> {

	private static Logger logger = LoggerFactory.getLogger(CloudParameterMessage.class);
	
	@Autowired
	@Qualifier("eventMessageProducer")
	private Producer<MessageAgent> producer;
	
	@Autowired
	private ParameterResolver parameterResolver;
	
	@Override
	public void process(MessageParameterAgent message) {
		logger.info("Request Parameters Agent: {}", message.getAgent());
		AgentParameter parameterCredential = parameterResolver.getParameterCredential(message.getAgent(), message.getNameBot());
		if(null != parameterCredential){
			message.setFieldValue(parameterCredential.getParameter().getFieldValue());			
		}
		producer.send("parameter."+message.getAgent(), message);
	}
}