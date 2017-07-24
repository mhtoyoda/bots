package com.fiveware.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fiveware.model.Agent;
import com.fiveware.model.Bot;


/**
 * Created by valdisnei on 13/07/17.
 */
@Service
public class ServiceAgent {

    public Agent save(Agent agent){
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8085/api/agent/save";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Agent> entity = new HttpEntity<Agent>(agent,headers);
        Agent agent1 = restTemplate.postForObject(url, entity, Agent.class);
        return agent1;
    }

    public Optional<Agent> findByNameAgent(String name){
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8085/api/agent/name/"+name;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<String>(name,headers);

        return restTemplate.getForObject(url, Optional.class);
    }

    public Agent findOne(Long id) {
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8085/api/agent/id/"+id;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Agent agent = restTemplate.getForObject(url, Agent.class);

        return agent;
    }

    public Long count() {
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8085/api/agent/count";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return restTemplate.getForObject(url, Long.class);
    }

    public List<Bot> findBotsByAgent(String nameAgent) {
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8085/api/agent/bots/nameAgent/"+nameAgent;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Bot[] objects = restTemplate.getForObject(url, Bot[].class);
        List<Bot> forObject = Arrays.asList(objects);
        return forObject;
    }
}
