package com.fiveware.context;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fiveware.service.ServiceCache;

@Component
public class ParameterContext {

	@Autowired
	private ServiceCache serviceCache;
	
	private String key;
	
	public void addQueueInContext(String parameter, String value){	
		serviceCache.add(parameter, value);		
	}
	
	public void removeQueueInContext(String parameter, String value){
		serviceCache.remove(parameter, value);		
	}
	
	public Set<String> getTasksQueues(String parameter) {
		return serviceCache.get(parameter);
	}
	
	public void setKey(String key){
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}
	
	public boolean hasParameter(){
		return serviceCache.has(this.getKey());
	}
}
