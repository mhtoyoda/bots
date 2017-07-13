package com.fiveware.service;

import com.fiveware.model.entities.Agent;
import com.fiveware.model.entities.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

/**
 * Created by valdisnei on 13/07/17.
 */
@Service
public class ServiceServer {


    @Autowired
    private RestTemplate restTemplate;

    public Server save(Server server){
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8085/api/server/save";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Server> entity = new HttpEntity<Server>(server,headers);
        return restTemplate.postForObject(url, entity, Server.class);
    }

    public Optional<Server> findByName(String nameServer) {

        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8085/api/server/name/"+nameServer;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<String>(nameServer,headers);

        Server server = restTemplate.getForObject(url, Server.class);
        return Optional.of(server);
    }

    public List<Agent> getAllAgent(String name) {
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8085/api/server/agents/name/"+name;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        List<Agent> listAgents = restTemplate.getForObject(url, List.class);

        return listAgents;
    }

    public Optional<List<Agent>> getAllAgentsByBotName(String serverName, String nameBot, String endpoint) {

        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8085/api/server/nameServer/"+serverName+"/nameBot/"+nameBot+"/endPoint/"+endpoint;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        List<Agent> listAgents = restTemplate.getForObject(url, List.class);

        return Optional.of(listAgents);
    }
}
