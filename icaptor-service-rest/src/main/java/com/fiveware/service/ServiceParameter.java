package com.fiveware.service;

import com.fiveware.config.ApiUrlPersistence;
import com.fiveware.model.Parameter;
import com.fiveware.model.ScopeParameter;
import com.fiveware.model.TypeParameter;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Service
public class ServiceParameter {

    static Logger logger = LoggerFactory.getLogger(ServiceParameter.class);

    @Autowired
    private ApiUrlPersistence apiUrlPersistence;

    @Autowired
    private RestTemplate restTemplate;

    public Parameter save(Parameter parameter) {
        String url = apiUrlPersistence.endPoint("parameter", "");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Parameter> entity = new HttpEntity<Parameter>(parameter, headers);
        return restTemplate.postForObject(url, entity, Parameter.class);
    }

    public List<Parameter> save(List<Parameter> parameters, Long id) {
        String url = null;
        if (Objects.isNull(id))
            url = apiUrlPersistence.endPoint("parameter", "/several");
        else
            url = apiUrlPersistence.endPoint("parameter", "/several/user/" + id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List<Parameter>> entity = new HttpEntity<List<Parameter>>(parameters, headers);
        ResponseEntity<List<Parameter>> exchange = restTemplate.exchange(url, HttpMethod.POST, entity, new ParameterizedTypeReference<List<Parameter>>() {
        });
        return exchange.getBody();
    }

    public void delete(Long parameter) {
        String url = apiUrlPersistence.endPoint("parameter/", parameter.toString());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Long> entity = new HttpEntity<Long>(parameter, headers);
        restTemplate.exchange(url, HttpMethod.DELETE, entity, Long.class);
    }

    public void delete(List<Parameter> parameters) {
        String url = apiUrlPersistence.endPoint("parameter", "/all");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List<Parameter>> entity = new HttpEntity<List<Parameter>>(parameters, headers);
        restTemplate.exchange(url, HttpMethod.DELETE, entity, List.class);
    }

    public TypeParameter saveTypeParameter(TypeParameter typeParameter) {
        String url = apiUrlPersistence.endPoint("parameter", "/type");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TypeParameter> entity = new HttpEntity<TypeParameter>(typeParameter, headers);
        return restTemplate.postForObject(url, entity, TypeParameter.class);
    }

    public List<ScopeParameter> getScopeParameterAll() {
        String url = apiUrlPersistence.endPoint("parameter", "/scope");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ScopeParameter> requestEntity = new HttpEntity<>(null, headers);
        ResponseEntity<List<ScopeParameter>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<ScopeParameter>>() {
        });
        List<ScopeParameter> scopeParameters = responseEntity.getBody();
        return scopeParameters;
    }

    public ScopeParameter getScopeParameterById(Long id) {
        String url = apiUrlPersistence.endPoint("parameter", "/scope/" + id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ScopeParameter scopeParameter = restTemplate.getForObject(url, ScopeParameter.class);
        return scopeParameter;
    }

    public Parameter getParameterById(Long id) {
        String url = apiUrlPersistence.endPoint("parameter/", id.toString());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Parameter parameter = restTemplate.getForObject(url, Parameter.class);
        return parameter;
    }

    public List<TypeParameter> getTypeParameterAll() {
        String url = apiUrlPersistence.endPoint("parameter", "/type");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TypeParameter> requestEntity = new HttpEntity<>(null, headers);
        ResponseEntity<List<TypeParameter>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<TypeParameter>>() {
        });
        List<TypeParameter> typeParameters = responseEntity.getBody();
        return typeParameters;
    }

    public TypeParameter getTypeParameterById(Long id) {
        String url = apiUrlPersistence.endPoint("parameter", "/type/" + id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        TypeParameter typeParameter = restTemplate.getForObject(url, TypeParameter.class);
        return typeParameter;
    }

    public TypeParameter getTypeParameterByName(String name) {
        String url = apiUrlPersistence.endPoint("parameter", "/type/name/" + name);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        TypeParameter typeParameter = restTemplate.getForObject(url, TypeParameter.class);
        return typeParameter;
    }

    public List<Parameter> getParameterByBot(String botName) {
        String url = apiUrlPersistence.endPoint("parameter", "/bot/" + botName);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Parameter> requestEntity = new HttpEntity<>(null, headers);
        ResponseEntity<List<Parameter>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<Parameter>>() {
        });
        List<Parameter> parameters = responseEntity.getBody();
        return parameters;
    }

    public List<Parameter> findParameterByScopeAndType(String nameScope, String nameType) {
        String url = apiUrlPersistence.endPoint("parameter", "/bot/" + nameScope + "/" + nameType);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Parameter> requestEntity = new HttpEntity<>(null, headers);
        ResponseEntity<List<Parameter>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<Parameter>>() {
        });
        List<Parameter> parameters = responseEntity.getBody();
        return parameters;
    }

    public List<Parameter> getParametersByBotAndScopeName(String botName, String scopeName) {
        String url = apiUrlPersistence.endPoint("parameter", "/bot-scope/" + botName + "/" + scopeName);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Parameter> requestEntity = new HttpEntity<>(null, headers);
        ResponseEntity<List<Parameter>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<Parameter>>() {
        });
        List<Parameter> parameters = responseEntity.getBody();
        return parameters;
    }

    public List<Parameter> getParametersByBotAndUserId(String botName, Long userId) {
        String url = apiUrlPersistence.endPoint("parameter", "/bot-user/" + botName + "/" + userId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Parameter> requestEntity = new HttpEntity<>(null, headers);
        ResponseEntity<List<Parameter>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<Parameter>>() {
        });
        List<Parameter> parameters = responseEntity.getBody();
        return parameters;
    }

    public List<Parameter> listParametersByScope(String nameScope) {
        String url = apiUrlPersistence.endPoint("parameter", "/scope-parameter/" + nameScope);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Parameter> requestEntity = new HttpEntity<>(null, headers);
        ResponseEntity<List<Parameter>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<Parameter>>() {
        });
        List<Parameter> parameters = responseEntity.getBody();
        return parameters;
    }

    public List<Parameter> getParametersAll(Long id) {
        String url = null;
        if (Objects.isNull(id))
            url = apiUrlPersistence.endPoint("parameter", "");
        else
            url = apiUrlPersistence.endPoint("parameter", "/user/" + id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Parameter> requestEntity = new HttpEntity<>(null, headers);
        ResponseEntity<List<Parameter>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<Parameter>>() {
        });
        List<Parameter> scopeParameters = responseEntity.getBody();
        return scopeParameters;
    }

}