package com.fiveware.service;

import java.util.List;
import java.util.Optional;

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

import com.fiveware.config.ApiUrlPersistence;
import com.fiveware.model.Bot;
import com.fiveware.model.BotFormatter;

@Service
public class ServiceBot {

	private static Logger logger = LoggerFactory.getLogger(ServiceBot.class);

	@Autowired
	private ApiUrlPersistence apiUrlPersistence;

	@Autowired
	private RestTemplate restTemplate;

	public Optional<Bot> findByNameBot(String nameBot) {
		String url = apiUrlPersistence.endPoint("bot","/name/" + nameBot);


		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		Bot bot = restTemplate.getForObject(url, Bot.class);
		return Optional.of(bot);
	}

	public List<Bot> findAll() {
		String url = apiUrlPersistence.endPoint("bot","");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		ParameterizedTypeReference<List<Bot>> typeReference = new ParameterizedTypeReference<List<Bot>>() {};
		ResponseEntity<List<Bot>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<HttpHeaders>(headers), typeReference);

		return responseEntity.getBody();
	}

	public Bot save(Bot bot) {
		String url = apiUrlPersistence.endPoint("bot","");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Bot> entity = new HttpEntity<Bot>(bot, headers);
		ResponseEntity<Bot> botResponseEntity = restTemplate.postForEntity(url, entity, Bot.class);
		return botResponseEntity.getBody();
	}

	public List<BotFormatter> findBotFormatter(String nameBot) {
		String url = apiUrlPersistence.endPoint("bot","/formatter/"+nameBot);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		ParameterizedTypeReference<List<BotFormatter>> typeReference = new ParameterizedTypeReference<List<BotFormatter>>() {};
		ResponseEntity<List<BotFormatter>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<HttpHeaders>(headers), typeReference);

		return responseEntity.getBody();	}
	
	public BotFormatter saveBotFormatter(BotFormatter botFormatter) {
		String url = apiUrlPersistence.endPoint("bot","/formatter");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<BotFormatter> entity = new HttpEntity<BotFormatter>(botFormatter, headers);
		ResponseEntity<BotFormatter> botResponseEntity = restTemplate.postForEntity(url, entity, BotFormatter.class);
		return botResponseEntity.getBody();
	}
	
	public void deleteBotFormatter(List<BotFormatter> formatters) {
        String url = apiUrlPersistence.endPoint("bot", "/formatter");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List<BotFormatter>> entity = new HttpEntity<List<BotFormatter>>(formatters, headers);
        restTemplate.exchange(url, HttpMethod.DELETE, entity, List.class);
    }
}