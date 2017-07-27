package com.fiveware.pulling;

import java.util.Optional;

public abstract class BrokerPulling<T> {

	public abstract boolean canPullingMessage();
	
	public abstract void processMessage(String botName, T obj);
	
	public abstract Optional<T> receiveMessage(String queueName);
	
	protected void pullMessage(String botName, String queue){
		if(canPullingMessage()){
			Optional<T> obj = receiveMessage(queue);
			obj.ifPresent(message -> {
				processMessage(botName, message);
			});			
		}
	}
}
