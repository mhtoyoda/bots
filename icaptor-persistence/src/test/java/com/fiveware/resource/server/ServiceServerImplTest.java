package com.fiveware.resource.server;

import com.fiveware.model.Agent;
import com.fiveware.model.Server;
import com.fiveware.repository.ServerRepository;
import com.fiveware.service.server.ServiceServerImpl;
import com.google.common.collect.Sets;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class ServiceServerImplTest {
    @Mock
    ServerRepository serverRepository;

    ServiceServerImpl serviceServer;

    Server server;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.serviceServer = new ServiceServerImpl(serverRepository);
        server=new Server();
        server.setName("testServer");

    }

    @Test
    public void save() throws Exception {

        when(serverRepository.findByName(server.getName())).thenReturn(Optional.of(server));

        when(serverRepository.save(server)).thenReturn(server);

        assertNotNull(serviceServer.save(server));

        verify(serverRepository).findByName(server.getName());
    }

    @Test
    public void findByName() throws Exception {
        Server server=new Server();
        when(serverRepository.findByName(anyString())).thenReturn(Optional.of(server));

        assertTrue(serviceServer.findByName(anyString()).isPresent());

        verify(serverRepository,times(1)).findByName(anyString());
    }

    @Test
    public void getAllAgent() throws Exception {
        Agent agent=new Agent();
        server.setAgents(Sets.newHashSet(agent));

        when(spy(server).getAgents()).thenReturn(server.getAgents());

        when(serverRepository.findByName(server.getName())).thenReturn(Optional.of(server));

        assertEquals(serviceServer.getAllAgent(server.getName()).size(),1);

        verify(serverRepository,times(1)).findByName(server.getName());
    }

    @Test
    public void findAll() throws Exception {
        Iterable<Server> servers= Lists.newArrayList(server);
        when(serverRepository.findAll()).thenReturn(servers);
        assertNotNull(serviceServer.findAll().iterator().next());
        verify(serverRepository,times(1)).findAll();
    }

    @Test
    public void getAllAgentsByBotName() throws Exception {

        Agent agent=new Agent();
        List<Agent> servers= Lists.newArrayList(agent);
        when(serverRepository.getAllAgentsByBotName(anyString(),anyString(),anyString())).thenReturn(Optional.of(servers));

        assertTrue(serviceServer.getAllAgentsByBotName(anyString(),anyString(),anyString()).isPresent());

        verify(serverRepository,times(1))
                .getAllAgentsByBotName(anyString(),anyString(),anyString());


    }

}