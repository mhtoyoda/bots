package com.fiveware;

import com.fiveware.email.Conector;
import com.fiveware.transport.EmailTransportConfiguration;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.InputStream;

@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore
public class IcaptorEmailApplicationTests {

	@Test
	public void byProperties() {

		final String yourAddress = "email destinatario";
		final String fromEmail="email remetente";

		InputStream resourceAsStream = getClass().getResourceAsStream("/application.properties");
		EmailTransportConfiguration.configure(resourceAsStream);


		Conector.Email.from(fromEmail).to(yourAddress)
				.withSubject("Mail API")
//					.withAttachment(path)
				.withBody("Demo message").send();

	}

	@Test
	public void byProgramatically() {

		final String yourAddress = "email destinatario";
		final String fromEmail="email remetente";

		EmailTransportConfiguration.configure("smtp.gmail.com", true,
				false, "icaptor@fiveware.com.br", "iCaptor1q2w3e4r");


		Conector.Email.from(fromEmail).to(yourAddress)
				.withSubject("Mail API")
//					.withAttachment(path)
				.withBody("Demo message").send();

	}

}
