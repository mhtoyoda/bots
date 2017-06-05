package com.fiveware;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jayway.restassured.specification.RequestSpecification;

import java.io.File;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Ignore
public class AgentIcaptorApplicationTests {

	@Autowired
	private MessageSource messageSource;

	@LocalServerPort
	private int port;

	@Value("${worker.dir}")
	private String workDir;

	RequestSpecification consultaCEP = null;

	@Before
	public void setup(){
		consultaCEP = given().port(port);
	}

	@Test
	public void callBotSucess() {
		consultaCEP.when().get("/api/{botName}/{endPoint}/{parameter}",
				"consultaCEP", "correios-bot", "01310-000")
				.then()
				.root("bot")
				.body("localidade",equalTo("São Paulo/SP"))
				.body("cep",equalTo("01310-000"));
	}

	@Test
	public void botNaoEncontrado() {
		String parametro = "consultaCEP2";
		String message = messageSource.getMessage("bot.notFound", new Object[]{parametro}, null);
		consultaCEP.when().get("/api/{botName}/{endPoint}/{parameter}",
				parametro, "correios-bot", "01310-000")
				.then().statusCode(HttpStatus.SC_BAD_REQUEST)
				.body("message",equalTo(message));
	}
	@Test
	public void endPointNaoEncontrado() {
		String parametro = "correios-bot2";
		String message = messageSource.getMessage("endPoint.notFound", new Object[]{parametro}, null);
		consultaCEP.when().get("/api/{botName}/{endPoint}/{parameter}",
				"consultaCEP", parametro, "01310-000")
				.then().statusCode(HttpStatus.SC_BAD_REQUEST)
				.body("message",equalTo(message));
	}

}
