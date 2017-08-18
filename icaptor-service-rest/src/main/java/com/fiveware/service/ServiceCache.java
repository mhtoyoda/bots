package com.fiveware.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

@Service
public class ServiceCache {

    @Autowired
    private RestTemplate restTemplate;

    public Boolean add(String key, String value){
        String url = "http://localhost:8085/api/cache";
        HttpEntity<Map<String, Set<String>>> entity = getEntity(key, value);
        ResponseEntity<Boolean> resp = restTemplate.postForEntity(url, entity, Boolean.class);
        return resp.getBody();
    }

    public Boolean remove(String key, String value){
        String url = "http://localhost:8085/api/cache";
        HttpEntity<Map<String, Set<String>>> entity = getEntity(key, value);
        ResponseEntity<Boolean> exchange = restTemplate.exchange(url, HttpMethod.DELETE, entity, Boolean.class);
        return exchange.getBody();
    }

    public Set list(){
        String url = "http://localhost:8085/api/cache";
        Set forObject = restTemplate.getForObject(url, Set.class);
        return forObject;
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


    private HttpEntity<Map<String, Set<String>>> getEntity(String key, String value) {
        Map<String, Set<String>> cache = new LinkedHashMap<>();
        Set<String> valor = new TreeSet<>();
        valor.add(value);
        cache.put(key,valor);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<Map<String,Set<String>>>(cache, headers);
    }
 }