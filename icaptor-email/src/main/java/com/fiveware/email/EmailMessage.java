package com.fiveware.email;

import com.fiveware.transport.EmailTransportException;
import com.fiveware.transport.PostalService;
import com.fiveware.validation.EmailAddressValidator;
import com.fiveware.validation.IncompleteEmailException;
import com.fiveware.validation.InvalidEmailAddressException;

import java.util.HashSet;
import java.util.Set;
/**
 * @author valdisnei
 */
class EmailMessage implements BuilderEmail, Email {

	private static EmailAddressValidator emailAddressValidator = new EmailAddressValidator();
	private static PostalService postalService = new PostalService();

	private String fromAddress;
	private Set<String> toAddresses = new HashSet<String>();
	private Set<String> ccAddresses = new HashSet<String>();
	private Set<String> bccAddresses = new HashSet<String>();
	private Set<String> attachments = new HashSet<String>();
	private String subject;
	private String body;

	public void send() {
		validateRequiredInfo();
		validateAddresses();
		sendMessage();
	}

	protected void validateRequiredInfo() {
		if (fromAddress == null) {
			throw new IncompleteEmailException("Email Remetente nao pode ser null");
		}
		if (toAddresses.isEmpty()) {
			throw new IncompleteEmailException(
					"Necessario informar no minimo um email");
		}
		if (subject == null) {
			throw new IncompleteEmailException("Titulo nao poder null");
		}
		if (body == null) {
			throw new IncompleteEmailException("Corpo de email nao pode ser null");
		}
	}

	protected void sendMessage() {
		try {
			postalService.send(this);
		} catch (Exception e) {
			throw new EmailTransportException("Email nao enviado: "
					+ e.getMessage(), e);
		}
	}

	public BuilderEmail from(String address) {
		this.fromAddress = address;
		return this;
	}

	public BuilderEmail to(String... addresses) {
		for (int i = 0; i < addresses.length; i++) {
			this.toAddresses.add(addresses[i]);
		}
		return this;
	}

	public BuilderEmail cc(String... addresses) {
		for (int i = 0; i < addresses.length; i++) {
			this.ccAddresses.add(addresses[i]);
		}
		return this;
	}

	public BuilderEmail bcc(String... addresses) {
		for (int i = 0; i < addresses.length; i++) {
			this.bccAddresses.add(addresses[i]);
		}
		return this;
	}

	public BuilderEmail withSubject(String subject) {
		this.subject = subject;
		return this;
	}

	public BuilderEmail withBody(String body) {
		this.body = body;
		return this;
	}
	
	public BuilderEmail withAttachment(String... attachments) {
 		for (int i = 0; i < attachments.length; i++) {
			this.attachments.add(attachments[i]);
		}
		return this;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public Set<String> getToAddresses() {
		return toAddresses;
	}

	public Set<String> getCcAddresses() {
		return ccAddresses;
	}

	public Set<String> getBccAddresses() {
		return bccAddresses;
	}
	
	public Set<String> getAttachments() {
		return attachments;
	}

	public String getSubject() {
		return subject;
	}

	public String getBody() {
		return body;
	}

	protected BuilderEmail validateAddresses() {
		if (!emailAddressValidator.validate(fromAddress)) {
			throw new InvalidEmailAddressException("Origem: " + fromAddress);
		}

		for (String email : toAddresses) {
			if (!emailAddressValidator.validate(email)) {
				throw new InvalidEmailAddressException("Destino: " + email);
			}
		}

		return this;
	}

	public static void setEmailAddressValidator(
			EmailAddressValidator emailAddressValidator) {
		EmailMessage.emailAddressValidator = emailAddressValidator;
	}

	public static void setPostalService(PostalService postalService) {
		EmailMessage.postalService = postalService;
	}


	
}