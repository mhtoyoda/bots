package com.fiveware.messaging;

public interface Receiver<T> {

	T receive(String queueName);

}
