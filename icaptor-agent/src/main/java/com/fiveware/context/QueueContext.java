package com.fiveware.context;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fiveware.service.ServiceCache;

@Component
public class QueueContext {

	@Autowired
	private ServiceCache serviceCache;
	
	private String key;
	
	private String keyValue;
	
	public void addQueueInContext(String bot, String queueName){	
		serviceCache.add(bot, queueName);		
	}
	
	public void removeQueueInContext(String bot, String queueName){
		serviceCache.remove(bot, queueName);		
	}
	
	public Set<String> getTasksQueues(String bot) {
		return serviceCache.get(bot);
	}
	
	public void setKey(String key){
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}
	
	public String getKeyValue() {
		return keyValue;
	}

	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}

	public boolean hasTask(){
		return serviceCache.has(this.getKey(), this.getKeyValue());
	}
	
	public void addQueueInContext(String bot, String keyValue, String queueName){	
		serviceCache.add(bot, keyValue, queueName);		
	}
	
	public void removeQueueInContext(String bot, String keyValue, String queueName){
		serviceCache.remove(bot, keyValue, queueName);		
	}
	
	public Set<String> getTasksQueues(String bot, String keyValue) {
		return serviceCache.get(bot, keyValue);
	}
}
