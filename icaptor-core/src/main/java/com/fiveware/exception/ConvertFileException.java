package com.fiveware.exception;

public class ConvertFileException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8199094209291867904L;

	public ConvertFileException(String error){
		super(error);
	}
}
