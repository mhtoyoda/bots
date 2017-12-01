package com.fiveware.service;

import com.fiveware.config.ApiUrlPersistence;
import com.fiveware.model.StatusProcessItemTask;
import com.fiveware.model.StatusProcessTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ServiceStatusProcessTask {

	private static Logger logger = LoggerFactory.getLogger(ServiceStatusProcessTask.class);


	@Autowired
	private ApiUrlPersistence apiUrlPersistence;

	@Autowired
	private RestTemplate restTemplate;

	@SuppressWarnings("unchecked")
	public List<StatusProcessTask> getAllPossibleTaskStatus() {
		String url = apiUrlPersistence.endPoint("status","");

		List<StatusProcessTask> statusList = restTemplate.getForObject(url, List.class);
		logger.debug("Loading all [{}] possible statuses for a task.", statusList.size());
		return statusList;
	}

	public StatusProcessTask getStatusProcessById(Long id) {
		String url = apiUrlPersistence.endPoint("status/",id.toString());

		StatusProcessTask status = restTemplate.getForObject(url, StatusProcessTask.class);
		return status;
	}

	public StatusProcessItemTask getStatusProcessItemTaskById(Long id) {
		String url = apiUrlPersistence.endPoint( "item/status/",id.toString());

		StatusProcessItemTask status = restTemplate.getForObject(url, StatusProcessItemTask.class);
		return status;
	}

	protected HttpHeaders getNecessaryHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}
}
