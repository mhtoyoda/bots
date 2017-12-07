package com.fiveware.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiveware.model.Bot;
import com.fiveware.model.Task;
import com.fiveware.model.user.IcaptorUser;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Random;

public class TestUtil {

    public static final String API_USER = "/api/usuario";
    public static final String API_BOT = "/api/bot";
    private static final String API_TASK = "/api/task";


    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private static TestUtil testUtil;

    private TestUtil(){}

    public static TestUtil getTestUtil(MockMvc mockMvc, ObjectMapper objectMapper) {

        if (testUtil==null)
           testUtil = new TestUtil(mockMvc, objectMapper);

        return testUtil;
    }

    public TestUtil(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }


    public Task saveTask() throws Exception{
        Task task = new Task();
        String json = objectMapper.writeValueAsString(task);
        ResultActions perform = postResultActions(json, API_TASK);
        String contentAsString = perform.andReturn().getResponse().getContentAsString();
        return objectMapper.readValue(contentAsString,Task.class);
    }


    public  IcaptorUser saveUser() throws Exception {
        IcaptorUser user = new IcaptorUser();
        user.setActive(true);
        user.setEmail("admin@icaptor.com");
        user.setName("Administrator");
        String json = objectMapper.writeValueAsString(user);
        ResultActions perform = postResultActions(json, API_USER);
        String contentAsString = perform.andReturn().getResponse().getContentAsString();
        return objectMapper.readValue(contentAsString,IcaptorUser.class);
    }

    public Bot saveBot(Bot bot) throws Exception {
        String json = objectMapper.writeValueAsString(bot);
        ResultActions perform = postResultActions(json, API_BOT);
        String contentAsString = perform.andReturn().getResponse().getContentAsString();
        return objectMapper.readValue(contentAsString,Bot.class);
    }

    public ResultActions postResultActions(String json, String endPoint) throws Exception {
        return mockMvc.perform(
                MockMvcRequestBuilders.post(endPoint)
                        .accept(MediaTypes.HAL_JSON)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON));
    }
    public ResultActions putResultActions(String json, String endPoint) throws Exception {
        return mockMvc.perform(
                MockMvcRequestBuilders.put(endPoint)
                        .accept(MediaTypes.HAL_JSON)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON));
    }

    public ResultActions deleteResultActions(String json, String endPoint) throws Exception {
        return mockMvc.perform(
                MockMvcRequestBuilders.delete(endPoint)
                        .accept(MediaTypes.HAL_JSON)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON));
    }

    public ResultActions getResultActions(String endPoint) throws Exception {
        return mockMvc.perform(
                MockMvcRequestBuilders.get(endPoint)
                        .accept(MediaTypes.HAL_JSON)
                        .contentType(MediaType.APPLICATION_JSON));
    }

    public String generateString(int length) {
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }

}
