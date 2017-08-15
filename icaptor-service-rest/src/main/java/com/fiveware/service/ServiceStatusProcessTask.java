package com.fiveware.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fiveware.model.StatuProcessItemTask;
import com.fiveware.model.StatuProcessTask;

@Service
public class ServiceStatusProcessTask {

	static Logger logger = LoggerFactory.getLogger(ServiceStatusProcessTask.class);

	@Autowired
	private RestTemplate restTemplate;
	
	public StatuProcessTask getStatusProcessById(Long id) {
		String url = "http://localhost:8085/api/status/" +id;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		StatuProcessTask status = restTemplate.getForObject(url, StatuProcessTask.class);
		return status;
	}
	
	public StatuProcessItemTask getStatusProcessItemTaskById(Long id) {
		String url = "http://localhost:8085/api/item/status/" +id;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		StatuProcessItemTask status = restTemplate.getForObject(url, StatuProcessItemTask.class);
		return status;
	}
}
