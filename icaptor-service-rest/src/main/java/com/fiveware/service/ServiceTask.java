package com.fiveware.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fiveware.model.Task;

@Service
public class ServiceTask {

	static Logger logger = LoggerFactory.getLogger(ServiceTask.class);

	@Autowired
	private RestTemplate restTemplate;

	public Task save(Task task) {
		String url = "http://localhost:8085/api/task/save";
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
		String url = "http://localhost:8085/api/task/id/" +id;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Task task = restTemplate.getForObject(url, Task.class);
		return task;
	}
}
