package com.fiveware.service.infra;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiveware.config.ApiUrlPersistence;
import com.fiveware.model.Agent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;


/**
 * Created by valdisnei on 01/11/17.
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
        String uri = null;
        uri = "http://localhost".concat(":").concat(String.valueOf(agent.getPort()))
                .concat("/management/metrics");

        logger.debug(uri);

        Map<String, Object> agentInfra = null;
        try {
            String forObject = restTemplate.getForObject(uri, String.class);
            agentInfra = objectMapper.readValue(forObject, Map.class);
            agentInfra.put("agent", agent);

            saveRedis(agent, agentInfra);
        } catch (IOException e) {
            logger.error("{}", e);
        } catch (Exception e) {
            logger.error("{}", e);
        }

        return agentInfra;
    }

    public void saveRedis(Agent agent, Map<String, Object> data) throws Exception {
        String url = apiUrlPersistence.endPointRedis("agent/", agent.getNameAgent());

        String metrics = objectMapper.writeValueAsString(data);

        restTemplate.postForEntity(url, metrics, String.class);
    }

    public Map<String, Object> getRedis(Agent agent) {
        String url = apiUrlPersistence.endPointRedis("agent/", agent.getNameAgent());
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);

        Map map = null;
        try {
            map = objectMapper.readValue(forEntity.getBody(), Map.class);
        } catch (IOException e) {
            logger.error("{}", e);
        }

        return map;

    }


}
