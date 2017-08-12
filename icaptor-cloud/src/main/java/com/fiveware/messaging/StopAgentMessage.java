package com.fiveware.messaging;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fiveware.model.Agent;
import com.fiveware.model.message.MessageAgent;
import com.fiveware.service.ServiceAgent;

@Component("STOP_AGENT")
public class StopAgentMessage implements ConsumerTypeMessage<MessageAgent> {

	private static Logger log = LoggerFactory.getLogger(StopAgentMessage.class);
	
	@Autowired
	private ServiceAgent serviceAgent;
	
	@Autowired
	private BrokerManager brokerManager;
	
	@Override
	public void process(MessageAgent message) {
		log.debug("Stop Agent {}",message.toString());
		Agent agent = new Agent.BuilderAgent().nameAgent(message.getAgent()).port(message.getPort()).build();
		serviceAgent.remove(agent);
		String queueName = String.format("tasks.%s.in", StringUtils.trim(message.getAgent()));
		brokerManager.deleteQueue(queueName);
	}

}
