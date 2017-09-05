package com.fiveware.service;

import com.fiveware.model.BotsMetric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ServiceSocket {

	private static final String BASE_URL = "http://localhost:8086";

	@Autowired
	private RestTemplate restTemplate;

	public void setPercent(Float percent){

		String url = BASE_URL + "/percents/"+percent;

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		restTemplate.getForObject(url, Float.class);
	}

	public void sendMetric(BotsMetric botsMetric) {

		String url = BASE_URL + "/bot-metric";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<BotsMetric> entity = new HttpEntity<BotsMetric>(botsMetric, headers);
		ResponseEntity<BotsMetric> botsMetricResponseEntity = restTemplate.postForEntity(url, entity, BotsMetric.class);
	}

}
