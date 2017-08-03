package com.fiveware.service;

import com.fiveware.model.Agent;
import com.fiveware.model.Bot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


/**
 * Created by valdisnei on 13/07/17.
 */
@Service
public class ServiceAgent {

    @Autowired
    private RestTemplate restTemplate;

    public Agent save(Agent agent){

        String url = "http://localhost:8085/api/agent";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Agent> entity = new HttpEntity<Agent>(agent,headers);
        Agent agent1 = restTemplate.postForObject(url, entity, Agent.class);
        return agent1;
    }

    public Optional<Agent> findByNameAgent(String name){

        String url = "http://localhost:8085/api/agent/"+name+"/name";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<String>(name,headers);

        return restTemplate.getForObject(url, Optional.class);
    }

    public Agent findOne(Long id) {

        String url = "http://localhost:8085/api/agent/"+id;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Agent agent = restTemplate.getForObject(url, Agent.class);

        return agent;
    }

    public Long count() {

        String url = "http://localhost:8085/api/agent/count";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return restTemplate.getForObject(url, Long.class);
    }

    public List<Bot> findBotsByAgent(String nameAgent) {

        String url = "http://localhost:8085/api/agent/bots/nameAgent/"+nameAgent;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Bot[] objects = restTemplate.getForObject(url, Bot[].class);
        List<Bot> forObject = Arrays.asList(objects);
        return forObject;
    }
}
