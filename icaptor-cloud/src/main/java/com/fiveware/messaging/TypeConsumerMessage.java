package com.fiveware.messaging;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.reflections.Reflections;
import org.springframework.stereotype.Component;

@Component
public class TypeConsumerMessage {

	private Set<Class<? extends ConsumerTypeMessage>> scan(String namePackage){
		Reflections reflections = new Reflections(namePackage);
		Set<Class<? extends ConsumerTypeMessage>> subTypesOf = reflections.getSubTypesOf(ConsumerTypeMessage.class);
		return subTypesOf;
	}
	
	public Map<String, ConsumerTypeMessage> getConsumer(String namePackage) throws InstantiationException, IllegalAccessException{
		Map<String, ConsumerTypeMessage> consumersMap = new ConcurrentHashMap<String, ConsumerTypeMessage>();
		Set<Class<? extends ConsumerTypeMessage>> consumerTypeMessages = scan(namePackage);
		for (Class<? extends ConsumerTypeMessage> consumerTypeMessage : consumerTypeMessages) {			
			Component component = consumerTypeMessage.getAnnotation(Component.class);
			consumersMap.put(component.value(), consumerTypeMessage.newInstance());			
		}
		return consumersMap;
	}
		
}
