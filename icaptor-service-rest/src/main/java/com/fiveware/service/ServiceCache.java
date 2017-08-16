package com.fiveware.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ServiceCache {

    @Autowired
    private RestTemplate restTemplate;

    public Boolean add(String key, String value){
        String url = "http://localhost:8085/api/cache/add/key/"+key+"/value/"+value;
        return restTemplate.getForObject(url, Boolean.class);
    }
    
    public Boolean remove(String key, String value){
        String url = "http://localhost:8085/api/cache/remove/key/"+key+"/value/"+value;
        return restTemplate.getForObject(url, Boolean.class);
    }
    
    public Set<String> get(String key){
        String url = "http://localhost:8085/api/cache/get/"+key;
        Set<String> values = restTemplate.getForObject(url, Set.class);
        return values;
    }
   
    public Boolean has(String key){
        String url = "http://localhost:8085/api/cache/has/"+key;
        boolean hasItem = restTemplate.getForObject(url, Boolean.class);
        return hasItem;
    }
 }