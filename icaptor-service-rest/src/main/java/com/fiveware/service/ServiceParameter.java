package com.fiveware.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fiveware.model.Parameter;
import com.fiveware.model.ScopeParameter;
import com.fiveware.model.TypeParameter;

@Service
public class ServiceParameter {

	static Logger logger = LoggerFactory.getLogger(ServiceParameter.class);

	@Autowired
	private RestTemplate restTemplate;

	public Parameter save(Parameter parameter) {
		String url = "http://localhost:8085/api/parameter";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Parameter> entity = new HttpEntity<Parameter>(parameter, headers);
		return restTemplate.postForObject(url, entity, Parameter.class);
	}
	
	public void delete(Parameter parameter) {
		String url = "http://localhost:8085/api/parameter";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Parameter> entity = new HttpEntity<Parameter>(parameter, headers);		
		restTemplate.exchange(url, HttpMethod.DELETE, entity, Parameter.class);
	}
	
	public void delete(List<Parameter> parameters) {
		String url = "http://localhost:8085/api/parameter/all";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<List<Parameter>> entity = new HttpEntity<List<Parameter>>(parameters, headers);		
		restTemplate.exchange(url, HttpMethod.DELETE, entity, List.class);
	}
	
	public TypeParameter saveTypeParameter(TypeParameter typeParameter) {
		String url = "http://localhost:8085/api/parameter/type";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<TypeParameter> entity = new HttpEntity<TypeParameter>(typeParameter, headers);
		return restTemplate.postForObject(url, entity, TypeParameter.class);
	}

	public List<ScopeParameter> getScopeParameterAll() {
		String url = "http://localhost:8085/api/parameter/scope";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		List<ScopeParameter> scopeParameters = restTemplate.getForObject(url, List.class);
		return scopeParameters;
	}
	
	public ScopeParameter getScopeParameterById(Long id) {
		String url = "http://localhost:8085/api/parameter/scope/" +id;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		ScopeParameter scopeParameter = restTemplate.getForObject(url, ScopeParameter.class);
		return scopeParameter;
	}
	
	public Parameter getParameterById(Long id) {
		String url = "http://localhost:8085/api/parameter/" +id;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Parameter parameter = restTemplate.getForObject(url, Parameter.class);
		return parameter;
	}
	
	public List<TypeParameter> getTypeParameterAll() {
		String url = "http://localhost:8085/api/parameter/type";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		List<TypeParameter> typeParameters = restTemplate.getForObject(url, List.class);
		return typeParameters;
	}
	
	public TypeParameter getTypeParameterById(Long id) {
		String url = "http://localhost:8085/api/parameter/type/" +id;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		TypeParameter typeParameter = restTemplate.getForObject(url, TypeParameter.class);
		return typeParameter;
	}
	
	public TypeParameter getTypeParameterByName(String name) {
		String url = "http://localhost:8085/api/parameter/type/name/" +name;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		TypeParameter typeParameter = restTemplate.getForObject(url, TypeParameter.class);
		return typeParameter;
	}
	
	public List<Parameter> getParameterByBot(String botName) {
		String url = "http://localhost:8085/api/parameter/bot/"+botName;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Parameter> requestEntity = new HttpEntity<>(null, headers);
		ResponseEntity<List<Parameter>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<Parameter>>() {});
		List<Parameter> parameters = responseEntity.getBody();
		return parameters;
	}
	
	public List<Parameter> findParameterByScopeAndType(String nameScope, String nameType) {
		String url = "http://localhost:8085/api/parameter/bot/"+nameScope+"/"+nameType;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		List<Parameter> parameters = restTemplate.getForObject(url, List.class);
		return parameters;
	}
}
