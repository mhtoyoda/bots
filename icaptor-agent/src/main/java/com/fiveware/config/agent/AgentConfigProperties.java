package com.fiveware.config.agent;

import com.fiveware.config.ICaptorApiProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AgentConfigProperties {


	@Autowired
	private ICaptorApiProperty iCaptorApiProperty;

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

	public String getIp() {return extractIp(iCaptorApiProperty.getServer().getHost());}

	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}

	public String getHost() {
		return iCaptorApiProperty.getServer().getHost();
	}

	public String extractIp(String url) {
//		String[] splitUp = url.split("/");
//		String ipAddress = splitUp[2];
		return url.split(":")[0];
	}
}