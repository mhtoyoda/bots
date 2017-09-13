package com.fiveware.service;

import com.fiveware.config.ApiUrlPersistence;
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
    private ApiUrlPersistence apiUrlPersistence;


    @Autowired
    private RestTemplate restTemplate;

    public Boolean add(String key, String value){
        String url = apiUrlPersistence.endPoint("cache","");

        HttpEntity<Map<String, Set<String>>> entity = getEntity(key, value);
        ResponseEntity<Boolean> resp = restTemplate.postForEntity(url, entity, Boolean.class);
        return resp.getBody();
    }

    public Boolean remove(String key, String value){
        String url = apiUrlPersistence.endPoint("cache","");

        HttpEntity<Map<String, Set<String>>> entity = getEntity(key, value);
        ResponseEntity<Boolean> exchange = restTemplate.exchange(url, HttpMethod.DELETE, entity, Boolean.class);
        return exchange.getBody();
    }

    public Set list(){
        String url = apiUrlPersistence.endPoint("cache","");

        Set forObject = restTemplate.getForObject(url, Set.class);
        return forObject;
    }
    
    public Set<String> get(String key){
        String url = apiUrlPersistence.endPoint("cache","/get/"+key);

        Set<String> values = restTemplate.getForObject(url, Set.class);
        return values;
    }
    
    public Boolean has(String key){
        String url = apiUrlPersistence.endPoint("cache","/has/"+key);

        boolean hasItem = restTemplate.getForObject(url, Boolean.class);
        return hasItem;
    }
    
    public Boolean add(String key, String keyValue, String value){
        String url = apiUrlPersistence.endPoint("cache","/hm");
        HttpEntity<Map<String, Map<String, Set<String>>>> entity = getEntity(key, keyValue, value);
        ResponseEntity<Boolean> resp = restTemplate.postForEntity(url, entity, Boolean.class);
        return resp.getBody();
    }

    public Boolean remove(String key, String keyValue, String value){
        String url = apiUrlPersistence.endPoint("cache","/hm");
        HttpEntity<Map<String, Map<String, Set<String>>>> entity = getEntity(key, keyValue, value);
        ResponseEntity<Boolean> exchange = restTemplate.exchange(url, HttpMethod.DELETE, entity, Boolean.class);
        return exchange.getBody();
    }

    public Set map(){
        String url = apiUrlPersistence.endPoint("cache","/hm");
        Set forObject = restTemplate.getForObject(url, Set.class);
        return forObject;
    }
    
    public Set<String> get(String key, String keyValue){
        String url = apiUrlPersistence.endPoint("cache","/hm/get/"+key+"/"+keyValue);

        Set<String> values = restTemplate.getForObject(url, Set.class);
        return values;
    }
   
    public Boolean has(String key, String keyValue){
        String url = apiUrlPersistence.endPoint("cache","/hm/has/"+key+"/"+keyValue);
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
    
    private HttpEntity<Map<String, Map<String, Set<String>>>> getEntity(String key, String keyValue, String value) {
    	Map<String, Map<String, Set<String>>> cache = new LinkedHashMap<>();
        Map<String, Set<String>> mapValues = new LinkedHashMap<>();
        Set<String> values = new TreeSet<>();
        values.add(value);
        mapValues.put(keyValue, values);    
        cache.put(key, mapValues);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<Map<String, Map<String, Set<String>>>>(cache, headers);
    }
 }