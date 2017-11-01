package com.fiveware.service;

import com.fiveware.config.ApiUrlPersistence;
import com.fiveware.model.Agent;
import com.fiveware.model.AgentParameter;
import com.fiveware.model.Bot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;


/**
 * Created by valdisnei on 13/07/17.
 */
@Service
public class ServiceAgent {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ApiUrlPersistence apiUrlPersistence;

    public Agent save(Agent agent){

        String url = apiUrlPersistence.endPoint("agent","");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Agent> entity = new HttpEntity<Agent>(agent,headers);
        Agent agent1 = restTemplate.postForObject(url, entity, Agent.class);
        return agent1;
    }

    public Agent findByNameAgent(String name){
        String url = apiUrlPersistence.endPoint("agent/",name+"/name");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Agent> requestEntity = new HttpEntity<>(null, headers);
        ResponseEntity<Agent> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<Agent>() {});
        return responseEntity.getBody();
    }

    public Agent findOne(Long id) {

        String url = apiUrlPersistence.endPoint("agent/",id.toString());


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Agent agent = restTemplate.getForObject(url, Agent.class);

        return agent;
    }

    public Long count() {

        String url = apiUrlPersistence.endPoint("agent","/count");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return restTemplate.getForObject(url, Long.class);
    }

    public List<Bot> findBotsByAgent(String nameAgent) {

        String url = apiUrlPersistence.endPoint("agent","/bots/nameAgent/"+nameAgent);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Bot[] objects = restTemplate.getForObject(url, Bot[].class);
        List<Bot> forObject = Arrays.asList(objects);
        return forObject;
    }

	public void remove(Agent agent) {
        String url = apiUrlPersistence.endPoint("agent","");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Agent> entity = new HttpEntity<Agent>(agent,headers);
        restTemplate.exchange(url, HttpMethod.DELETE, entity, Agent.class);        		
	}
	
	public List<Agent> findAgentsByBotName(String nameBot) {
        String url = apiUrlPersistence.endPoint("agent","/agents/nameBot/"+nameBot);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Agent[] objects = restTemplate.getForObject(url, Agent[].class);
        List<Agent> forObject = Arrays.asList(objects);
        return forObject;
    }
	
	 public AgentParameter findByAgentParameterId(@PathVariable("parameterId") Long parameterId){	        
         String url = apiUrlPersistence.endPoint("agent","/parameter/id/"+parameterId);

		 HttpHeaders headers = new HttpHeaders();
	     headers.setContentType(MediaType.APPLICATION_JSON);	        
	     HttpEntity<AgentParameter> requestEntity = new HttpEntity<>(null, headers);	      
	     ResponseEntity<AgentParameter> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<AgentParameter>(){});	        	      
	     AgentParameter agentParameters = responseEntity.getBody();	     
	     return agentParameters;   
	 }
	 
	 public AgentParameter findByAgentName(@PathVariable("nameAgent") String nameAgent){	        
         String url = apiUrlPersistence.endPoint("agent","/parameter/nameAgent/"+nameAgent);

         HttpHeaders headers = new HttpHeaders();
	     headers.setContentType(MediaType.APPLICATION_JSON);	        
	     HttpEntity<AgentParameter> requestEntity = new HttpEntity<>(null, headers);	      
	     ResponseEntity<AgentParameter> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<AgentParameter>(){});	        	      
	     AgentParameter agentParameters = responseEntity.getBody();	     
	     return agentParameters;   
	 }
	    
	 public AgentParameter save(AgentParameter agentParameter){	   
         String url = apiUrlPersistence.endPoint("agent","/parameter");

         HttpHeaders headers = new HttpHeaders();
		 headers.setContentType(MediaType.APPLICATION_JSON);
	     HttpEntity<AgentParameter> entity = new HttpEntity<AgentParameter>(agentParameter,headers);
	     AgentParameter agentParamter = restTemplate.postForObject(url, entity, AgentParameter.class);	     
	     return agentParamter;	    
	 }
	 
	 public void remove(AgentParameter agentParameter) {
            String url = apiUrlPersistence.endPoint("agent","/parameter");

            HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);

	        HttpEntity<AgentParameter> entity = new HttpEntity<AgentParameter>(agentParameter, headers);
	        restTemplate.exchange(url, HttpMethod.DELETE, entity, AgentParameter.class);        		
		}

    public List<Agent> findAll() {
        String url = apiUrlPersistence.endPoint("agent","");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Agent[] objects = restTemplate.getForObject(url, Agent[].class);
        List<Agent> forObject = Arrays.asList(objects);

        return forObject;
    }
}
