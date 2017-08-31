package com.fiveware.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fiveware.model.Bot;

@Service
public class ServiceBot {

	private static Logger logger = LoggerFactory.getLogger(ServiceBot.class);

	private static final String BASE_URL = "http://localhost:8085/api/bot";

	@Autowired
	private RestTemplate restTemplate;

	public Optional<Bot> findByNameBot(String nameBot) {
		String url = BASE_URL + "/name/" + nameBot;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		Bot bot = restTemplate.getForObject(url, Bot.class);
		return Optional.of(bot);
	}

	public List<Bot> findAll() {
		return restTemplate.getForObject(BASE_URL, List.class);
	}

	public Bot save(Bot bot) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Bot> entity = new HttpEntity<Bot>(bot, headers);
		ResponseEntity<Bot> botResponseEntity = restTemplate.postForEntity(BASE_URL, entity, Bot.class);
		return botResponseEntity.getBody();
	}

}