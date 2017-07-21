package com.fiveware.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fiveware.model.Agent;
import com.fiveware.model.Server;

/**
 * Created by valdisnei on 13/07/17.
 */
@Service
public class ServiceServer {


    @Autowired
    private RestTemplate restTemplate;

    public Server save(Server server){

        String url = "http://localhost:8085/api/server/save";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity entity = new HttpEntity(server,headers);

        ResponseEntity<Server> response = restTemplate.exchange(url,HttpMethod.POST, entity, Server.class);
        return response.getBody();
    }

    public Optional<Server> findByName(String nameServer) {


        String url = "http://localhost:8085/api/server/name/"+nameServer;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<String>(nameServer,headers);

        Server server = restTemplate.getForObject(url, Server.class);
        return Optional.of(server);
    }

    public List<Agent> getAllAgent(String name) {

        String url = "http://localhost:8085/api/server/agents/name/"+name;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        List<Agent> listAgents = restTemplate.getForObject(url, List.class);

        return listAgents;
    }

    public Optional<List<Agent>> getAllAgentsByBotName(String serverName, String nameBot, String endpoint) {

        String url = "http://localhost:8085/api/server/nameServer/"+serverName+"/nameBot/"+nameBot+"/endPoint/"+endpoint;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        List<Agent> listAgents = restTemplate.getForObject(url, List.class);

        return Optional.of(listAgents);
    }
}
