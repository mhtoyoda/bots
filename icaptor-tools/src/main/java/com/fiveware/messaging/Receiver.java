package com.fiveware.messaging;

public interface Receiver<T> {

	void receive(TypeMessage typeMessage);

}
