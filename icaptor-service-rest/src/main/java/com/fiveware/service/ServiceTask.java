package com.fiveware.service;

import com.fiveware.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ServiceTask {

	static Logger logger = LoggerFactory.getLogger(ServiceTask.class);

	@Autowired
	private RestTemplate restTemplate;

	public Task save(Task task) {
		String url = "http://localhost:8085/api/task";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Task> entity = new HttpEntity<Task>(task, headers);
		return restTemplate.postForObject(url, entity, Task.class);
	}

	public List<Task> getAllAgent(String nameBot, String status) {
		String url = "http://localhost:8085/api/task/bot/" + nameBot + "/status/" + status;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		List<Task> tasks = restTemplate.getForObject(url, List.class);
		return tasks;
	}
	
	public Task getTaskById(Long id) {
		String url = "http://localhost:8085/api/task/" +id;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Task task = restTemplate.getForObject(url, Task.class);
		return task;
	}

	public Task updateStatus(Task task, String status) {
		String url = "http://localhost:8085/api/task/" +status+"/status";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity entity = new HttpEntity(task,headers);
		ResponseEntity<Task> exchange = restTemplate.exchange(url, HttpMethod.PUT, entity, Task.class);
		return exchange.getBody();
	}
}
