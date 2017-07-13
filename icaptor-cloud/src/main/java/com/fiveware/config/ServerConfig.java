package com.fiveware.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerConfig {

	@Autowired
	private ServerInfo server;

	public ServerInfo getServer() {
		return server;
	}

}