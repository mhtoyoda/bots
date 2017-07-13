package com.fiveware.exception;

public class BotNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public BotNotFoundException(String error) {
		super(error);
	}	
}
