package com.fiveware.messaging;

public interface Producer<T> {

	void send(TypeMessage typeMessage, String message);

}
