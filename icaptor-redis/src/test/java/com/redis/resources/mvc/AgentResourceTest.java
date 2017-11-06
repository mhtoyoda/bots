package com.redis.resources.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redis.resources.MockMvcTest;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import sun.management.Agent;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * com.redis.resources
 * Created by valdisnei on 03/11/2017
 */
@Ignore
public class AgentResourceTest extends MockMvcTest{

    Logger log = LoggerFactory.getLogger(AgentResourceTest.class);

    @Test
    public void get() throws Exception {
        //given
        Agent agent = new Agent();

        String content = new ObjectMapper().writeValueAsString(agent);
        log.debug("param json ==== {}", content);

        //when
        this.mockMvc
                .perform(post("/agent/123456")
                        //example parameter setting
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(content)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .characterEncoding(CHARSET)
                )
        //then
        .andExpect(status().isCreated())
        .andReturn();
    }

//    @Test
//    public void save() throws Exception {
//        //given
//        String key = "valdis";
//
//        //when
//        this.mockMvc
//                .perform(get("/agent/" + key)
//                        .characterEncoding(CHARSET)
//                )
//        //then
//        .andExpect(status().isOk())
//        .andExpect(jsonPath("$.name").value(key))
//        .andExpect(jsonPath("$.message").isNotEmpty())
//        .andReturn();
//    }
}