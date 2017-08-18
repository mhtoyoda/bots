package com.fiveware.messaging;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fiveware.model.message.MessageAgent;
import com.fiveware.model.message.MessageParameterAgent;
import com.fiveware.model.message.MessageTaskAgent;

@Component
public class TypeConsumerMessage {

	static Logger log = LoggerFactory.getLogger(TypeConsumerMessage.class);
	
	@Autowired
	@Qualifier("STOP_AGENT")
	private ConsumerTypeMessage<MessageAgent> stopAgent;
	
	@Autowired
	@Qualifier("START_AGENT")
	private ConsumerTypeMessage<MessageAgent> startAgent;

	@Autowired
	@Qualifier("KEEP_ALIVE")
	private ConsumerTypeMessage<MessageAgent> keepAlive;

	@Autowired
	@Qualifier("PURGE_QUEUES")
	private ConsumerTypeMessage<MessageAgent> purgeQueues;
	
	@Autowired
	@Qualifier("ITEM_TASK_PROCESSING")
	private ConsumerTypeMessage<MessageTaskAgent> updateItemTask;

	@Autowired
	@Qualifier("PARAMETERS")
	private ConsumerTypeMessage<MessageParameterAgent> parameters;
	
	private Set<Class<? extends ConsumerTypeMessage>> scan(String namePackage){
		Reflections reflections = new Reflections(namePackage);
		Set<Class<? extends ConsumerTypeMessage>> subTypesOf = reflections.getSubTypesOf(ConsumerTypeMessage.class);
		return subTypesOf;
	}
	
	public Map<String, ConsumerTypeMessage> getConsumer(String namePackage) {
		Map<String, ConsumerTypeMessage> consumersMap = new ConcurrentHashMap<String, ConsumerTypeMessage>();
		Set<Class<? extends ConsumerTypeMessage>> consumerTypeMessages = scan(namePackage);
		consumerTypeMessages.forEach(consumerTypeMessage -> {
			Component component = consumerTypeMessage.getAnnotation(Component.class);
			try {				
				consumersMap.put(component.value(), consumerTypeMessage.newInstance());
			} catch (InstantiationException | IllegalAccessException e) {
				log.error("Error ConsumerTypeMessage: {}", e);
			}
		});
		return consumersMap;
	}
	
	public Map<String, ConsumerTypeMessage> loadConsumer() {
		Map<String, ConsumerTypeMessage> consumersMap = new ConcurrentHashMap<String, ConsumerTypeMessage>();
		consumersMap.put("KEEP_ALIVE", keepAlive);
		consumersMap.put("START_AGENT", startAgent);
		consumersMap.put("STOP_AGENT", stopAgent);
		consumersMap.put("PURGE_QUEUES", purgeQueues);
		consumersMap.put("ITEM_TASK_PROCESSING", updateItemTask);
		consumersMap.put("PARAMETERS", parameters);
		return consumersMap;
	}
}