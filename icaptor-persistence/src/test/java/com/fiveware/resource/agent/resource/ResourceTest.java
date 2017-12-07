package com.fiveware.resource.agent.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiveware.IcaptorPersistenceApplication;
import com.fiveware.model.Agent;
import com.fiveware.model.Bot;
import com.fiveware.resource.TestUtil;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.fiveware.resource.TestUtil.getTestUtil;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = IcaptorPersistenceApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ResourceTest {


    private static final String API_AGENT = "/api/agent";
    private static final String API_AGENT_REMOVE = "/api/agent";
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;
    private TestUtil testUtil;
    Agent agent;

    @Before
    public void setUp() throws Exception {
        objectMapper = new ObjectMapper();
       testUtil = getTestUtil(mockMvc, objectMapper);
       agent = new Agent();

    }

    @Test
    public void save() throws Exception {
        Agent agent = new Agent();
        agent.setNameAgent(testUtil.generateString(20));
        String json = objectMapper.writeValueAsString(agent);

        ResultActions perform = testUtil.postResultActions(json, API_AGENT);
        perform.andExpect((status().isCreated()))
                .andExpect(jsonPath("id").value("2"))
                .andExpect(jsonPath("nameAgent").value(agent.getNameAgent()));

    }

    @Test
    public void remove() throws Exception {
        Agent agent = new Agent();
        agent.setNameAgent(testUtil.generateString(20));
        String json = objectMapper.writeValueAsString(agent);

        ResultActions resultActions = testUtil.postResultActions(json, API_AGENT);

        json=resultActions.andReturn().getResponse().getContentAsString();

        ResultActions perform = testUtil.deleteResultActions(json, API_AGENT_REMOVE);
        perform.andExpect(status().isNoContent());

    }

    @Test
    public void findByNameAgent() throws Exception {
    }

    @Test
    public void findOne() throws Exception {
    }

    @Test
    public void findAll() throws Exception {
    }

    @Test
    public void count() throws Exception {
    }

    @Test
    public void findBotsByAgent() throws Exception {
    }

    @Test
    public void findByNameBot() throws Exception {
    }

    @Test
    public void findByParameterId() throws Exception {
    }

    @Test
    public void findByAgentName() throws Exception {
    }

    @Test
    public void save1() throws Exception {
    }

    @Test
    public void remove1() throws Exception {
    }


}