package com.fiveware.messaging;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fiveware.model.Parameter;
import com.fiveware.model.message.MessageAgent;
import com.fiveware.model.message.MessageParameterAgent;
import com.fiveware.parameter.ParameterInfo;
import com.fiveware.parameter.ParameterResolver;
import com.google.common.collect.Lists;

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
		List<String> list = Lists.newArrayList();
		Parameter parameterCredential = parameterResolver.getParameterCredential(message.getAgent(), message.getNameBot());
		if(null != parameterCredential){
			list.add("credential:"+parameterCredential.getTypeParameter().getName()+":"+parameterCredential.getFieldValue());			
		}
		
		ParameterInfo parameterInfo = parameterResolver.getParameterByBot(message.getNameBot());
		Map<String, Parameter> parameters = parameterInfo.getParameters();
		if(null != parameters){
			for(Map.Entry<String, Parameter> parameter : parameters.entrySet()){			 
				String param = "parameter:"+parameter.getKey()+":"+parameter.getValue().getFieldValue();			 
				list.add(param);
			}
		}
		message.setFieldValue(list);
		producer.send("parameter."+message.getAgent(), message);
	}
}