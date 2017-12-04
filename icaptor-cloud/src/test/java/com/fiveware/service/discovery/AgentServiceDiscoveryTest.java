package com.fiveware.service.discovery;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fiveware.exception.AgentBotNotFoundException;
import com.fiveware.model.Agent;
import com.fiveware.service.ServiceServer;
import com.google.common.collect.Lists;

public class AgentServiceDiscoveryTest {

	@Mock
	private ServiceServer serviceServer;

	@InjectMocks
	private AgentServiceDiscovery agentServiceDiscovery;

	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void getUrlServiceTest() throws AgentBotNotFoundException{
		String serverName = "icaptor-cloud";
		String nameBot = "cep";
		String endpoint = "cep-consulta";

		Agent agent = new Agent();
		agent.setIp("127.0.0.1");
		agent.setPort(8080);
		List<Agent> list = Lists.newArrayList(agent);
		Optional<List<Agent>> agents = Optional.of(list);
		when(serviceServer.getAllAgentsByBotName(serverName, nameBot, endpoint)).thenReturn(agents);
		assertEquals("http://127.0.0.1:8080/api/cep/cep-consulta", agentServiceDiscovery.getUrlService(serverName, nameBot, endpoint));		
	}
	
	@Test(expected = AgentBotNotFoundException.class)
	public void getUrlServiceEmptyTest() throws AgentBotNotFoundException {
		String serverName = "icaptor-cloud";
		String nameBot = "cep";
		String endpoint = "cep-consulta";

		List<Agent> list = Lists.newArrayList();
		Optional<List<Agent>> agents = Optional.of(list);
		when(serviceServer.getAllAgentsByBotName(serverName, nameBot, endpoint)).thenReturn(agents);		
		agentServiceDiscovery.getUrlService(serverName, nameBot, endpoint);
	}
}
