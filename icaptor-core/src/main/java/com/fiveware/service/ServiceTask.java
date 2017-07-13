package com.fiveware.service;

import com.fiveware.model.entities.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
public class ServiceTask {

	static Logger logger = LoggerFactory.getLogger(ServiceTask.class);
	
	public Task save(Task task) {
		RestTemplate restTemplate = new RestTemplate();

		String url = "http://localhost:8085/api/server/task/save";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Task> entity = new HttpEntity<Task>(task,headers);
		return restTemplate.postForObject(url, entity, Task.class);
	}
}
