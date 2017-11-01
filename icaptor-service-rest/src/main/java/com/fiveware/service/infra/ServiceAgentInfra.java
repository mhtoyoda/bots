package com.fiveware.service.infra;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiveware.config.ApiUrlPersistence;
import com.fiveware.model.Agent;
import com.fiveware.model.AgentParameter;
import com.fiveware.model.Bot;
import com.fiveware.model.infra.AgentInfra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * Created by valdisnei on 13/07/17.
 */
@Service
public class ServiceAgentInfra {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ApiUrlPersistence apiUrlPersistence;


    @Autowired
    private ObjectMapper objectMapper;


    public Map<String,Object> metrics(Agent agent){
        String uri = "http://localhost:".concat(String.valueOf(agent.getPort())).concat("/management/metrics");

        String forObject = restTemplate.getForObject(uri, String.class);
        Map<String,Object> agentInfra =null;
        try {
            agentInfra = objectMapper.readValue(forObject, Map.class);
            agentInfra.put("agent",agent);
            System.out.println("agentInfra = " + agentInfra);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return agentInfra;
    }


}
