package com.fiveware.email;
/**
 * @author valdisnei
 */
public interface BuilderEmail extends Conector {

	BuilderEmail from(String address);

	BuilderEmail to(String... addresses);

	BuilderEmail cc(String... addresses);

	BuilderEmail bcc(String... addresses);

	BuilderEmail withSubject(String subject);

	BuilderEmail withBody(String body);
	
	BuilderEmail withAttachment(String... attachments);

	void send();


}