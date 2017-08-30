package com.fiveware.exception;

public class ParameterInvalidException extends Exception {

	public ParameterInvalidException(){
		super();
	}
	
	public ParameterInvalidException(String error){
		super(error);
	}
}
