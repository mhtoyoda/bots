package com.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * com.redis
 * Created by valdisnei on 03/11/2017
 */
@SpringBootApplication
public class ICaptorRedisApplication extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(ICaptorRedisApplication.class, args);
    }

}
