package com.fiveware.service.discovery;


import com.fiveware.exception.AgentBotNotFoundException;

public interface ServiceDiscovery {
	String getUrlService(String serverName, String nameBot, String endpoint) throws AgentBotNotFoundException;
}
