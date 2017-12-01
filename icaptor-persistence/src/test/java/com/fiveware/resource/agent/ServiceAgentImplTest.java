package com.fiveware.resource.agent;

import com.fiveware.model.Agent;
import com.fiveware.model.AgentParameter;
import com.fiveware.model.Bot;
import com.fiveware.repository.AgentParameterRepository;
import com.fiveware.repository.AgentRepository;
import com.fiveware.service.agent.ServiceAgentImpl;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class ServiceAgentImplTest {

    @Mock
    AgentRepository agentRepository;

    @Mock
    AgentParameterRepository agentParameterRepository;

    ServiceAgentImpl serviceAgent;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        serviceAgent = new ServiceAgentImpl(agentParameterRepository, agentRepository);
    }

    @Test
    public void save() throws Exception {
        Agent agent=new Agent();

        when(agentRepository.findAll()).thenReturn(Lists.newArrayList(agent));
        when(serviceAgent.save(agent)).thenReturn(agent);


        assertEquals(agentRepository.findAll().iterator().next(),agent);
        verify(agentRepository,times(1)).findAll();
        verify(agentRepository,times(1)).findByNameAgent(agent.getNameAgent());

    }

    @Test
    public void remove() throws Exception {
        Agent agent = new Agent();
        agent.setNameAgent("agent-001");

        when(agentRepository.findByNameAgent("agent-001")).thenReturn(agent);
        spy(serviceAgent).remove(agent);

        assertTrue(Optional.ofNullable(agent).isPresent());
        verify(agentRepository ,times(1)).delete(agent);
        verify(agentRepository ,times(1)).findByNameAgent("agent-001");

    }

    @Test
    public void findByNameAgent(){
        Agent agent = new Agent();
        agent.setNameAgent("agent-0002");

        when(serviceAgent.findByNameAgent("agent-0002")).thenReturn(agent);

        assertEquals(serviceAgent.findByNameAgent("agent-0002").getNameAgent(),"agent-0002");

        verify(agentRepository,times(1)).findByNameAgent("agent-0002");
    }

    @Test
    public void findOne(){
        Agent agent = new Agent();
        agent.setId(1L);
        when(serviceAgent.findOne(1L)).thenReturn(agent);

        assertEquals(serviceAgent.findOne(1L),agent);

        verify(agentRepository,times(1)).findOne(1L);
    }

    @Test
    public void count(){
        when(serviceAgent.count()).thenReturn(10l);

        assertEquals(serviceAgent.count(),new Long(10));

        verify(agentRepository,times(1)).count();
    }

    @Test
    public void findBotsByAgent(){
        Bot bot = new Bot();
        List<Bot> listBots = Lists.newArrayList(bot);
        when(serviceAgent.findBotsByAgent("agent-001")).thenReturn(listBots);

        assertEquals(serviceAgent.findBotsByAgent("agent-001").size(),1L);

        verify(agentRepository,times(1)).findBynameAgent("agent-001");
    }


    @Test
    public void findByNameBot(){
        Agent agent =new Agent();
        when(serviceAgent.findByNameBot("bot")).thenReturn(Lists.newArrayList(agent));

        assertEquals(serviceAgent.findByNameBot("bot").size(),1L);

        verify(agentRepository,times(1)).findByBot("bot");
    }

    @Test
    public void findByParameterId(){
        AgentParameter agentParameter = new AgentParameter();
        agentParameter.setId(1L);
        when(serviceAgent.findByParameterId(1L)).thenReturn(agentParameter);

        assertEquals(serviceAgent.findByParameterId(1L).getId(),new Long(1L));

        verify(agentParameterRepository ,times(1)).findByParameterId(1L);
    }

    @Test
    public void findByAgentName(){
        Agent agent = new Agent();
        when(serviceAgent.findByNameAgent("agent-001")).thenReturn(agent);

        assertEquals(serviceAgent.findByNameAgent("agent-001"),agent);

        verify(agentRepository ,times(1)).findByNameAgent("agent-001");
    }

    @Test
    public void saveAgentParameter(){
        AgentParameter agentParameter = new AgentParameter();
        when(serviceAgent.save(agentParameter)).thenReturn(agentParameter);

        assertEquals(serviceAgent.save(agentParameter).getUseDate().getHour(),LocalDateTime.now().getHour());

        verify(agentParameterRepository ,times(1)).save(agentParameter);

    }

    @Test
    public void removeAgentParameter(){
        AgentParameter agentParameter = new AgentParameter();
        agentParameter.setId(1L);

        when(agentParameterRepository.findOne(agentParameter.getId())).thenReturn(agentParameter);
        spy(serviceAgent).remove(agentParameter);

        assertTrue(Optional.ofNullable(agentParameter).isPresent());
        verify(agentParameterRepository ,times(1)).delete(agentParameter);
        verify(agentParameterRepository ,times(1)).findOne(agentParameter.getId());

    }

}