package com.fiveware.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fiveware.model.user.IcaptorUser;

@Service
public class ServiceUser {

	private static Logger logger = LoggerFactory.getLogger(ServiceUser.class);

	private static final String BASE_URL = "http://localhost:8085/api/usuario/";

	@Autowired
	private RestTemplate restTemplate;

	public Optional<IcaptorUser> getUserById(Long id) {
		String url = BASE_URL + id;

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		IcaptorUser user = restTemplate.getForObject(url, IcaptorUser.class);
		logger.debug("Retrieved user [{}] by ID [{}]", user.getName(), id);
		return Optional.of(user);
	}

	public Optional<IcaptorUser> getUserByEmail(String email) {
		String url = BASE_URL + "email";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		IcaptorUser user = new IcaptorUser();
		user.setEmail(email);
		HttpEntity<IcaptorUser> entity = new HttpEntity<>(user, headers);

		user = restTemplate.postForObject(url, entity, IcaptorUser.class);
		logger.debug("Retrieved user [{}] by email [{}]", user.getName(), email);
		return Optional.of(user);
	}
}
