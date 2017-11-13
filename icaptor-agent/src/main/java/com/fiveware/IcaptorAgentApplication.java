package com.fiveware;

import com.fiveware.config.ICaptorApiProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpManagementOperations;
import org.springframework.amqp.rabbit.core.RabbitManagementTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class IcaptorAgentApplication {
	static Logger log = LoggerFactory.getLogger(IcaptorAgentApplication.class);

	private final Environment env;

	public IcaptorAgentApplication(Environment env) {
		this.env = env;
	}

	@LocalServerPort
	private int port;


	public static void main(String[] args) throws UnknownHostException {
		SpringApplication app = new SpringApplication(IcaptorAgentApplication.class);

//		DefaultProfileUtil.addDefaultProfile(app);
		Map<String, Object> defProperties =  new HashMap<>();
        /*
        * The default profile to use when no other profiles are defined
        * This cannot be set in the <code>application.yml</code> file.
        * See https://github.com/spring-projects/spring-boot/issues/1219
        */
		defProperties.put("spring.profiles.default", "dev");
		app.setDefaultProperties(defProperties);
		Environment env = app.run(args).getEnvironment();
		String protocol = "http";
		if (env.getProperty("server.ssl.key-store") != null) {
			protocol = "https";
		}
		log.info("\n----------------------------------------------------------\n\t" +
						"Application '{}' is running! Access URLs:\n\t" +
						"Local: \t\t{}://localhost:{}\n\t" +
						"External: \t{}://{}:{}\n\t" +
						"Profile(s): \t{}\n----------------------------------------------------------",
				env.getProperty("spring.application.name"),
				protocol,
				env.getProperty("server.port"),
				protocol,
				InetAddress.getLocalHost().getHostAddress(),
				env.getProperty("server.port"),
				env.getActiveProfiles());

		String secretKey = env.getProperty("jhipster.security.authentication.jwt.secret");
		if (secretKey == null ) {
			log.error("\n----------------------------------------------------------\n" +
					"Your JWT secret key is not set up, you will not be able to log into the JHipster.\n"+
					"Please read the documentation at http://www.jhipster.tech/jhipster-registry/\n" +
					"----------------------------------------------------------");
		} else if (secretKey.equals("this-secret-should-not-be-used-read-the-comment")) {
			log.error("\n----------------------------------------------------------\n" +
					"Your JWT secret key is not configured using Spring Cloud Config, you will not be able to \n"+
					"use the JHipster Registry dashboards to monitor external applications. \n" +
					"Please read the documentation at http://www.jhipster.tech/jhipster-registry/\n" +
					"----------------------------------------------------------");
		}

	}


	@Autowired
	private ICaptorApiProperty iCaptorApiProperty;
	
	@Bean
	public AmqpManagementOperations amqpManagementOperations(){
		String uri = String.format("http://%s:%d/api/", iCaptorApiProperty.getBroker().getHost(), iCaptorApiProperty.getBroker().getPort());
		return new RabbitManagementTemplate(uri, iCaptorApiProperty.getBroker().getUser(), iCaptorApiProperty.getBroker().getPass());
	}
}