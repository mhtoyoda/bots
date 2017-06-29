package com.fiveware.config.agent;

import org.springframework.boot.context.embedded.EmbeddedServletContainerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class AgentListener implements ApplicationListener<EmbeddedServletContainerInitializedEvent>  {

	public int agentPort;
	
	@Override
	public void onApplicationEvent(EmbeddedServletContainerInitializedEvent event) {
		agentPort = event.getEmbeddedServletContainer().getPort();		
	}
	
	public int getAgentPort() {
		return agentPort;
	}
}
