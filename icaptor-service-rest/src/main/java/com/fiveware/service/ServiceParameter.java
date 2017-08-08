package com.fiveware.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
	
}
