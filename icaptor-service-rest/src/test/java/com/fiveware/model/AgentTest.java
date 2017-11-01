package com.fiveware.model;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class AgentTest {

    Agent agent;

    @Before
    public void setUp() throws Exception {
        agent = new Agent();
    }

    @Test
    public void getId() throws Exception {
        Long id= 1L;
        agent.setId(id);
        assertEquals(id,agent.getId());
    }

    @Test
    public void getNameAgent() throws Exception {
    }

    @Test
    public void getIp() throws Exception {
    }

}