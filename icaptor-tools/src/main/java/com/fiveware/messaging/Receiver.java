package com.fiveware.messaging;

public interface Receiver<T> {

	T receive(QueueName queueName);

}
