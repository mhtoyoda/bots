package com.fiveware.context;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;

import com.google.common.collect.Sets;

@Component
public class QueueContext {

	private static Map<String,Set<String>> tasksQueues = new LinkedHashMap<String,Set<String>>();
	
	public void addQueueInContext(String bot, String queueName){
		if(tasksQueues.containsKey(bot)){
			tasksQueues.get(bot).add(queueName);
		}else{
			Set<String> queues = new HashSet<String>();
			queues.add(queueName);
			tasksQueues.put(bot, queues);			
		}
	}
	
	public void removeQueueInContext(String bot, String queueName){
		if(tasksQueues.containsKey(bot)){
			Predicate<String> filter = p-> p.equals(queueName);
			tasksQueues.get(bot).removeIf(filter);
		}
	}
	
	public Set<String> getTasksQueues(String bot) {
		return tasksQueues.get(bot) == null ? Sets.newHashSet() : tasksQueues.get(bot);
	}
	
	public boolean hasTask(){
		return !tasksQueues.isEmpty();
	}
}
