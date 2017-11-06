package com.redis.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * com.redis.resources
 * Created by Valdisnei on 03/11/2017
 */
@RestController
@RequestMapping("/agent")
public class AgentResource {


    Logger logger = LoggerFactory.getLogger(AgentResource.class);

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @GetMapping(value = "/{key}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public String get(@PathVariable("key") String key) {
        String user = redisTemplate.opsForValue().get(key);
        logger.debug("{}", user);
        return user;
    }

    @RequestMapping(value = "/{key}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@PathVariable String key, @RequestBody String metrics) {
        redisTemplate.opsForValue().set(key, metrics);

        String s = redisTemplate.opsForValue().get(key);

        logger.debug("{}", s);

    }
}
