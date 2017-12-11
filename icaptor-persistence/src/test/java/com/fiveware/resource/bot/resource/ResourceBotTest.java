package com.fiveware.resource.bot.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiveware.IcaptorPersistenceApplication;
import com.fiveware.model.Bot;
import com.fiveware.model.Task;
import com.fiveware.model.user.IcaptorUser;
import com.fiveware.repository.BotRepository;
import com.fiveware.repository.InputFieldRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.fiveware.resource.TestUtil.API_BOT;
import static com.fiveware.resource.TestUtil.getTestUtil;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = IcaptorPersistenceApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ResourceBotTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    private InputFieldRepository inputFieldRepository;

    @Autowired
    private BotRepository botRepository;

    @Before
    public void setUp() throws Exception {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void save() throws Exception {

        Bot bot=new Bot();
        bot.setNameBot("bot-4");
        bot.setSeparatorFile("|");

        bot.setFieldsInput("cep");

        String json = objectMapper.writeValueAsString(bot);

        ResultActions perform = getTestUtil(mockMvc,objectMapper).postResultActions(json, API_BOT);
        perform.andExpect((status().isCreated()))
                .andDo(print());

        assertEquals(inputFieldRepository.count(),1);

        assertEquals(botRepository.findByNameBot("bot-4").get().totalInputFields(),1);

    }

    @Test
    public void findAll() throws Exception {
    }

    @Test
    public void findByNameBot() throws Exception {
    }

    @Test
    public void findBotFormatter() throws Exception {
    }

    @Test
    public void saveBotFormatter() throws Exception {
    }

    @Test
    public void deleteBotFormatter() throws Exception {
    }

    @Test
    public void listInputFields() throws Exception {

    }

}