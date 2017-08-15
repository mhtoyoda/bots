package com.fiveware.pulling;

import com.fiveware.exception.ExceptionBot;

import java.util.Optional;

public abstract class BrokerPulling<T> {

	public abstract boolean canPullingMessage();
	
	public abstract void processMessage(String botName, T obj) throws ExceptionBot;
	
	public abstract Optional<T> receiveMessage(String queueName);

	protected void pullMessage(String botName, String queue) throws ExceptionBot{
		if(canPullingMessage()){
			Optional<T> obj = receiveMessage(queue);
			obj.ifPresent((T message) -> {
				try {
					processMessage(botName, message);
				} catch (ExceptionBot exceptionBot) {
					throw exceptionBot;
				}
			});
		}
	}
}
