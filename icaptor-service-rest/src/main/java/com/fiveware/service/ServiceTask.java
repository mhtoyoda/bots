package com.fiveware.service;

import java.util.List;

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

import com.fiveware.model.StatuProcessTask;
import com.fiveware.model.Task;

@Service
public class ServiceTask {

	private static Logger logger = LoggerFactory.getLogger(ServiceTask.class);
	
	private static final String BASE_URL = "http://localhost:8085/api/task";

	@Autowired
	private RestTemplate restTemplate;

	public Task save(Task task) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Task> entity = new HttpEntity<Task>(task, headers);
		return restTemplate.postForObject(BASE_URL, entity, Task.class);
	}

	public List<Task> getAll() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Task> requestEntity = new HttpEntity<>(null, headers);
		ResponseEntity<List<Task>> responseEntity = restTemplate.exchange(BASE_URL, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<Task>>() {});		
		List<Task> tasks = responseEntity.getBody();
		return tasks;
	}

	public Task getTaskById(Long id) {
		String url = BASE_URL + "/" + id;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Task task = restTemplate.getForObject(url, Task.class);
		return task;
	}

	public Task updateStatus(Long id, StatuProcessTask status) {
		String url = BASE_URL + "/" + id + "/status";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Task task = new Task();
		task.setStatusProcess(status);
		HttpEntity<Task> entity = new HttpEntity<Task>(task, headers);

		ResponseEntity<Task> exchange = restTemplate.exchange(url, HttpMethod.PUT, entity, Task.class);
		return exchange.getBody();
	}

	public List<Task> getTaskByBot(String botName) {
		String url = BASE_URL + "/nameBot/" + botName;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Task> requestEntity = new HttpEntity<>(null, headers);
		ResponseEntity<List<Task>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<Task>>() {});		
		List<Task> tasks = responseEntity.getBody();		
		return tasks;
	}

	public List<Task> getTaskByStatus(String status) {
		String url = BASE_URL + "/status/" + status;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Task> httpEntity = new HttpEntity<>(null, headers);
		ResponseEntity<List<Task>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<List<Task>>() {});
		List<Task> tasks = responseEntity.getBody();
		return tasks;
	}
	
	public List<Task> getTaskByUserIdOrderedByLoadTime(Long userId) {
		String url = BASE_URL + "/user/" + userId;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);	
		HttpEntity<Task> requestEntity = new HttpEntity<>(null, headers);
		ResponseEntity<List<Task>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<Task>>() {});		
		List<Task> tasks = responseEntity.getBody();
		logger.debug("Loaded [{}] tasks for user id [{}]", tasks.size(), userId);
		return tasks;
	}
	
}
