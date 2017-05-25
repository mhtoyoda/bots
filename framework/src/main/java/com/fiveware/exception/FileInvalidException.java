package com.fiveware.exception;

public class FileInvalidException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6891350134618236467L;

	public FileInvalidException(String message){
		super(message);
	}
}
