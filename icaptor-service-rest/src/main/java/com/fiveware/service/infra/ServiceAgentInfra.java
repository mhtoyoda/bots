package com.fiveware.service.infra;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiveware.config.ApiUrlPersistence;
import com.fiveware.model.Agent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;


/**
 * Created by valdisnei on 13/07/17.
 */
@Service
public class ServiceAgentInfra {

    static Logger logger = LoggerFactory.getLogger(ServiceAgentInfra.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ApiUrlPersistence apiUrlPersistence;


    @Autowired
    private ObjectMapper objectMapper;


    public Map<String, Object> metrics(Agent agent) {
        String uri = "http://".concat(agent.getAddressHost()).concat(":")
                .concat(String.valueOf(agent.getPort()))
                .concat("/management/metrics");

        logger.debug(uri);

        String forObject = restTemplate.getForObject(uri, String.class);
        Map<String, Object> agentInfra = null;
        try {
            agentInfra = objectMapper.readValue(forObject, Map.class);
            agentInfra.put("agent", agent);
        } catch (IOException e) {
            logger.error("{}", e);
        }

        return agentInfra;
    }


}
