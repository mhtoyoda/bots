package com.fiveware;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by valdisnei on 25/05/17.
 */

@Configuration
public class Config {

    @Bean
    public ObjectMapper objectMapper(){return new ObjectMapper();}
}
