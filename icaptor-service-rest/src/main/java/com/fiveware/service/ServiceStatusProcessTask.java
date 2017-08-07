package com.fiveware.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fiveware.model.StatuProcess;

@Service
public class ServiceStatusProcessTask {

	static Logger logger = LoggerFactory.getLogger(ServiceStatusProcessTask.class);

	@Autowired
	private RestTemplate restTemplate;
	
	public StatuProcess getStatusProcessById(Long id) {
		String url = "http://localhost:8085/api/status/" +id;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		StatuProcess status = restTemplate.getForObject(url, StatuProcess.class);
		return status;
	}
}
