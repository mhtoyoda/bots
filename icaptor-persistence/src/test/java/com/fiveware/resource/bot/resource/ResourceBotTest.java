package com.fiveware.resource.bot.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiveware.IcaptorPersistenceApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = IcaptorPersistenceApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ResourceBotTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;


    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void save() throws Exception {
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