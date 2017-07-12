package com.fiveware.messaging;

public interface ConsumerTypeMessage<T> {

	void process(T message);
}
