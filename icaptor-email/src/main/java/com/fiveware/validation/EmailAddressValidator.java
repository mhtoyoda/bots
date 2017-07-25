package com.fiveware.validation;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;


/**
 * @author valdisnei
 */
public class EmailAddressValidator {

	public boolean validate(String emailAddress) {
		if (emailAddress == null) {
			return false;
		}

		try {
			new InternetAddress(emailAddress, true);
		} catch (AddressException e) {
			return false;
		}

		return true;
	}
}
