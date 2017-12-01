package com.fiveware.service;

import java.net.URI;
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
import org.springframework.web.util.UriComponentsBuilder;

import com.fiveware.config.ApiUrlPersistence;
import com.fiveware.model.StatusProcessTask;
import com.fiveware.model.Task;

@Service
public class ServiceTask {

	private static Logger logger = LoggerFactory.getLogger(ServiceTask.class);

	@Autowired
	private ApiUrlPersistence apiUrlPersistence;

	@Autowired
	private ServiceActivity activityService;

	@Autowired
	private RestTemplate restTemplate;

	public Task save(Task task) {
		String BASE_URL = apiUrlPersistence.endPoint("task", "");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Task> entity = new HttpEntity<Task>(task, headers);
		Task taskSaved = restTemplate.postForObject(BASE_URL, entity, Task.class);
		activityService.save("task.created", new Object[] { taskSaved.getId() }, taskSaved.getUsuario().getId());
		return taskSaved;
	}

	public List<Task> getAll() {
		String BASE_URL = apiUrlPersistence.endPoint("task", "");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Task> requestEntity = new HttpEntity<>(null, headers);
		ResponseEntity<List<Task>> responseEntity = restTemplate.exchange(BASE_URL, HttpMethod.GET, requestEntity,
				new ParameterizedTypeReference<List<Task>>() {
				});
		List<Task> tasks = responseEntity.getBody();
		return tasks;
	}

	public Task getTaskById(Long id) {
		String BASE_URL = apiUrlPersistence.endPoint("task", "");

		String url = BASE_URL + "/" + id;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Task task = restTemplate.getForObject(url, Task.class);
		return task;
	}

	public Task updateStatus(Long id, StatusProcessTask status) {
		String BASE_URL = apiUrlPersistence.endPoint("task", "");

		String url = BASE_URL + "/" + id + "/status";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Task task = new Task();
		task.setStatusProcess(status);
		HttpEntity<Task> entity = new HttpEntity<Task>(task, headers);

		ResponseEntity<Task> exchange = restTemplate.exchange(url, HttpMethod.PUT, entity, Task.class);
		Task taskUpdated = exchange.getBody();

		activityService.save("task.status.updated", new Object[] { taskUpdated.getId(), taskUpdated.getStatusProcess().getName() }, taskUpdated.getUsuario().getId());

		return taskUpdated;
	}

	public List<Task> getTaskByBot(String botName) {
		String BASE_URL = apiUrlPersistence.endPoint("task", "");

		String url = BASE_URL + "/nameBot/" + botName;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Task> requestEntity = new HttpEntity<>(null, headers);
		ResponseEntity<List<Task>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
				new ParameterizedTypeReference<List<Task>>() {
				});
		List<Task> tasks = responseEntity.getBody();
		return tasks;
	}

	public List<Task> getTaskByStatus(String status) {
		String BASE_URL = apiUrlPersistence.endPoint("task", "");

		String url = BASE_URL + "/status/" + status;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Task> httpEntity = new HttpEntity<>(null, headers);
		ResponseEntity<List<Task>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity,
				new ParameterizedTypeReference<List<Task>>() {
				});
		List<Task> tasks = responseEntity.getBody();
		return tasks;
	}

	public List<Task> getTaskByUserIdOrderedByLoadTime(Long userId) {
		String BASE_URL = apiUrlPersistence.endPoint("task", "");

		String url = BASE_URL + "/user/" + userId;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Task> requestEntity = new HttpEntity<>(null, headers);
		ResponseEntity<List<Task>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
				new ParameterizedTypeReference<List<Task>>() {
				});
		List<Task> tasks = responseEntity.getBody();
		logger.debug("Loaded [{}] tasks for user id [{}]", tasks.size(), userId);
		return tasks;
	}

	public List<Task> searchForTaskWithFilters(List<String> status, String botId, String dataInicial, String dataFinal,
			List<String> usersId) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		String BASE_URL = apiUrlPersistence.endPoint("task", "");

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(BASE_URL);

		if (status != null && !status.isEmpty()) {
			builder.queryParam("taskStatusIds", String.join(",", status));
		}

		if (botId != null) {
			builder.queryParam("botsIds", botId);
		}

		if (dataInicial != null && dataFinal != null) {
			builder.queryParam("startedDate", formatDateParam(dataInicial));
			builder.queryParam("finishedDate", formatDateParam(dataFinal));
		}

		if (usersId != null && !usersId.isEmpty()) {
			builder.queryParam("usersIds", String.join(",", usersId));
		}

		HttpEntity<Task> requestEntity = new HttpEntity<>(null, headers);

		URI uri = builder.build().encode().toUri();
		ResponseEntity<List<Task>> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, requestEntity,
				new ParameterizedTypeReference<List<Task>>() {
				});
		return responseEntity.getBody();
	}

	protected String formatDateParam(String date) {
		String[] split = date.split("[/]");
		StringBuilder sb = new StringBuilder();
		sb.append(split[2]).append('-');
		sb.append(split[1]).append('-');
		sb.append(split[0]);
		sb.append(" 00:00:00");

		return sb.toString();
	}

	public List<Task> getTaskRecentByStatus(String status) {
		String BASE_URL = apiUrlPersistence.endPoint("task/recent", "");

		String url = BASE_URL + "/status/" + status;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Task> httpEntity = new HttpEntity<>(null, headers);
		ResponseEntity<List<Task>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity,
				new ParameterizedTypeReference<List<Task>>() {
				});
		List<Task> tasks = responseEntity.getBody();
		return tasks;
	}
}
