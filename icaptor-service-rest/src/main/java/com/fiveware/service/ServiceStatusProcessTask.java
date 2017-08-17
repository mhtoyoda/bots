package com.fiveware.service;

import java.util.List;

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

	private static Logger logger = LoggerFactory.getLogger(ServiceStatusProcessTask.class);

	private static final String BASE_URL = "http://localhost:8085/api";
	private static final String TASK_URL = BASE_URL + "/status";
	private static final String TASK_ITEN_URL = BASE_URL + "/item/status";

	@Autowired
	private RestTemplate restTemplate;

	@SuppressWarnings("unchecked")
	public List<StatuProcessTask> getAllPossibleTaskStatus() {
		List<StatuProcessTask> statusList = restTemplate.getForObject(TASK_URL, List.class);
		logger.debug("Loading all [{}] possible statuses for a task.", statusList.size());
		return statusList;
	}

	public StatuProcessTask getStatusProcessById(Long id) {
		String url = TASK_URL + "/" + id;
		StatuProcessTask status = restTemplate.getForObject(url, StatuProcessTask.class);
		return status;
	}

	public StatuProcessItemTask getStatusProcessItemTaskById(Long id) {
		String url = TASK_ITEN_URL + "/" + id;
		StatuProcessItemTask status = restTemplate.getForObject(url, StatuProcessItemTask.class);
		return status;
	}

	protected HttpHeaders getNecessaryHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}
}
