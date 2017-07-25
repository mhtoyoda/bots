package com.fiveware.validation;

/**
 * @author valdisnei
 */
public class InvalidEmailAddressException extends RuntimeException {

	public InvalidEmailAddressException(String message) {
		super(message);
	}
}
