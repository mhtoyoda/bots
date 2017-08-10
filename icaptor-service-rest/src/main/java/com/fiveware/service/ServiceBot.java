package com.fiveware.service;

import com.fiveware.model.Bot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

/**
 * Created by valdisnei on 29/05/17.
 */
@Service
public class ServiceBot {

    static Logger logger = LoggerFactory.getLogger(ServiceBot.class);

    @Autowired
    private RestTemplate restTemplate;


    public Optional<Bot> findByNameBot(String nameBot){

        String url = "http://localhost:8085/api/bot/name/"+nameBot;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Bot bot = restTemplate.getForObject(url, Bot.class);
        return Optional.of(bot);
    }


    public Bot save(Bot bot){
        String url = "http://localhost:8085/api/bot";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Bot> entity = new HttpEntity<Bot>(bot,headers);
        ResponseEntity<Bot> botResponseEntity = restTemplate.postForEntity(url, entity, Bot.class);
        return botResponseEntity.getBody();
    }

}