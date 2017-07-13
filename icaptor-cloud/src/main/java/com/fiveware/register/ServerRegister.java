package com.fiveware.register;

import com.fiveware.config.ServerConfig;
import com.fiveware.config.ServerInfo;
import com.fiveware.model.entities.Server;
import com.fiveware.service.ServiceServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Component
public class ServerRegister {

	private static final Logger log = LoggerFactory.getLogger(ServerRegister.class);

	@Autowired
	private ServerConfig serverConfig;

	@Autowired
	private ServiceServer serviceServer;

	@PostConstruct
	public void register() {
		ServerInfo server = serverConfig.getServer();
		log.info("Init Server Host: {} - Port: {}", server.getHost(), server.getPort());
		registerServer();
	}

	private void registerServer() {
		ServerInfo serverInfo = serverConfig.getServer();
		Optional<Server> optional = serviceServer.findByName(serverInfo.getName());
		if (!optional.isPresent()) {
			Server server = new Server();
			server.setName(serverInfo.getName());
			String host = serverInfo.getHost() + ":" + serverInfo.getPort();
			server.setHost(host);
			serviceServer.save(server);
			log.info("Register Server Host: {}", host);
		}
	}
}
