package com.fiveware.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fiveware.model.entities.Agent;
import com.fiveware.model.entities.Bot;

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
        return restTemplate.postForObject(url, entity, Agent.class);
    }

    public Optional<Agent> findByNameAgent(String name){
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8085/api/agent/"+name;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<String>(name,headers);

        Agent agent = restTemplate.getForObject(url, Agent.class);

        return Optional.of(agent);
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

        return restTemplate.getForObject(url, List.class);
    }
}
