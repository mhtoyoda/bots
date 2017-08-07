package com.fiveware.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fiveware.model.Usuario;

@Service
public class ServiceUser {

	static Logger logger = LoggerFactory.getLogger(ServiceUser.class);

	@Autowired
	private RestTemplate restTemplate;
	
	public Usuario getUserById(Long id) {
		String url = "http://localhost:8085/api/usuario/" +id;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Usuario user = restTemplate.getForObject(url, Usuario.class);
		return user;
	}
}
