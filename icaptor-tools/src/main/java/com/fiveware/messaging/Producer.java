package com.fiveware.messaging;

public interface Producer<T> {

	void send(String queueName, T message);

}
