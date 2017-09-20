package com.fiveware.service;

import com.fiveware.config.ApiUrlPersistence;
import com.fiveware.model.Agent;
import com.fiveware.model.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.Arrays.asList;

/**
 * Created by valdisnei on 13/07/17.
 */
@Service
public class ServiceServer {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ApiUrlPersistence apiUrlPersistence;

    public Server save(Server server){
        String url = apiUrlPersistence.endPoint("server","");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity entity = new HttpEntity(server,headers);

        ResponseEntity<Server> response = restTemplate.exchange(url,HttpMethod.POST, entity, Server.class);
        return response.getBody();
    }

    public Optional<Server> findByName(String nameServer) {
        String url = apiUrlPersistence.endPoint("server/",nameServer+"/name");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Server server = restTemplate.getForObject(url, Server.class);
        return Optional.of(server);
    }

    public List<Agent> getAllAgent(String name) {
        String url = apiUrlPersistence.endPoint("server","/agents/"+name+"/name");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Agent[] forObject = restTemplate.getForObject(url, Agent[].class);

        return !Objects.isNull(forObject)?asList(forObject):Collections.emptyList();
    }

    public Optional<List<Agent>> getAllAgentsByBotName(String serverName, String nameBot, String endpoint) {
        String url = apiUrlPersistence.endPoint("server","/nameServer/"+serverName+"/nameBot/"+nameBot+"/endPoint/"+endpoint);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        List<Agent> listAgents = restTemplate.getForObject(url, List.class);

        return Optional.of(listAgents);
    }
}
