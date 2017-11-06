package com.redis.resources;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * com.redis.web
 * Created by valdisnei on 03/11/2017
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public abstract class MockMvcTest {

    @Autowired
    protected WebApplicationContext wac;
    protected MockMvc mockMvc;
    protected static final String CHARSET = "UTF-8";

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.wac)
                .alwaysDo(MockMvcResultHandlers.print())//log in console
                .build();//mockmvc create
    }
}
