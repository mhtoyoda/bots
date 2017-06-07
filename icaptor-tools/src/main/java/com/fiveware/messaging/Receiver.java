package com.fiveware.messaging;

public interface Receiver<T> {

	String receive(TypeMessage typeMessage);

}
