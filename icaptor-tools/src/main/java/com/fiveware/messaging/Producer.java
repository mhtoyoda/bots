package com.fiveware.messaging;

public interface Producer<T> {

	void send(T message);

}