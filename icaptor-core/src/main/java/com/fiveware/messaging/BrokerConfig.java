package com.fiveware.messaging;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BrokerConfig {

	@Value("${broker.host}")
	private String host;
	
	@Value("${broker.port}")
	private Integer port;
	
	@Value("${broker.user}")
	private String user;
	
	@Value("${broker.pass}")
	private String pass;
	
	public String getHost() {
		return host;
	}

	public String getUser() {
		return user;
	}

	public String getPass() {
		return pass;
	}
	
	public Integer getPort() {
		return port;
	}

}
