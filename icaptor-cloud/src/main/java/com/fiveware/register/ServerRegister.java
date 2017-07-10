package com.fiveware.register;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fiveware.ServerConfig;
import com.fiveware.ServerConfig.ServerInfo;
import com.fiveware.model.entities.Server;
import com.fiveware.repository.ServerRepository;

import javax.annotation.PostConstruct;

@Component
public class ServerRegister {

	private static final Logger log = LoggerFactory.getLogger(ServerRegister.class);

	@Autowired
	private ServerConfig serverConfig;

	@Autowired
	private ServerRepository serverRepository;

	@PostConstruct
	public void register() {
		ServerInfo server = serverConfig.getServer();
		log.info("Init Server Host: {} - Port: {}", server.getHost(), server.getPort());
		registerServer();
	}

	private void registerServer() {
		ServerInfo serverInfo = serverConfig.getServer();
		Optional<Server> optional = serverRepository.findByName(serverInfo.getName());
		if (!optional.isPresent()) {
			Server server = new Server();
			server.setName(serverInfo.getName());
			String host = serverInfo.getHost() + ":" + serverInfo.getPort();
			server.setHost(host);
			serverRepository.save(server);
			log.info("Register Server Host: {}", host);
		}
	}
}
