package com.fiveware.service;

import com.fiveware.config.ApiUrlPersistence;
import com.fiveware.model.user.IcaptorUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceUser {

	private static Logger logger = LoggerFactory.getLogger(ServiceUser.class);

	@Autowired
	private ApiUrlPersistence apiUrlPersistence;

	@Autowired
	private RestTemplate restTemplate;

	public Optional<IcaptorUser> getUserById(Long id) {
		String BASE_URL = apiUrlPersistence.endPoint("usuario/","");
		String url = BASE_URL + id;

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		IcaptorUser user = restTemplate.getForObject(url, IcaptorUser.class);
		logger.debug("Retrieved user [{}] by ID [{}]", user.getName(), id);
		return Optional.of(user);
	}

	public Optional<IcaptorUser> getUserByEmail(String email) {
		String BASE_URL = apiUrlPersistence.endPoint("usuario/","");
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

	public List<IcaptorUser> getAll() {
		String BASE_URL = apiUrlPersistence.endPoint("usuario/","");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<HttpHeaders> requestEntity = new HttpEntity<HttpHeaders>(headers);

		ParameterizedTypeReference<List<IcaptorUser>> typeReference = new ParameterizedTypeReference<List<IcaptorUser>>() {};
		ResponseEntity<List<IcaptorUser>> exchanged = restTemplate.exchange(BASE_URL, HttpMethod.GET, requestEntity, typeReference);
		return exchanged.getBody();
	}
}
