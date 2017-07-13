package com.fiveware.service;


import com.fiveware.exception.AgentBotNotFoundException;

public interface ServiceDiscovery {
	String getUrlService(String serverName, String nameBot, String endpoint) throws AgentBotNotFoundException;
}
