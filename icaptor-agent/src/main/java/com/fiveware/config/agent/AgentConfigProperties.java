package com.fiveware.config.agent;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AgentConfigProperties {
	
	//@Value("${agent}")
	public String agentName;
	
	@Value("${icaptor.server.ip}")
	public String ip;
	
	@Value("${icaptor.server.name}")
	public String server;
	
	@Value("${icaptor.server.host}")
	public String host;
	
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {		
		this.agentName = agentName;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	
}