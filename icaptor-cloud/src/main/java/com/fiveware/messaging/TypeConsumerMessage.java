package com.fiveware.messaging;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TypeConsumerMessage {

	static Logger log = LoggerFactory.getLogger(TypeConsumerMessage.class);
	
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
}