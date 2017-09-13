package com.fiveware.service;

import com.fiveware.config.ApiUrlPersistence;
import com.fiveware.model.StatuProcessItemTask;
import com.fiveware.model.StatuProcessTask;
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
	public List<StatuProcessTask> getAllPossibleTaskStatus() {
		String url = apiUrlPersistence.endPoint("status","");

		List<StatuProcessTask> statusList = restTemplate.getForObject(url, List.class);
		logger.debug("Loading all [{}] possible statuses for a task.", statusList.size());
		return statusList;
	}

	public StatuProcessTask getStatusProcessById(Long id) {
		String url = apiUrlPersistence.endPoint("status/",id.toString());

		StatuProcessTask status = restTemplate.getForObject(url, StatuProcessTask.class);
		return status;
	}

	public StatuProcessItemTask getStatusProcessItemTaskById(Long id) {
		String url = apiUrlPersistence.endPoint( "item/status/",id.toString());

		StatuProcessItemTask status = restTemplate.getForObject(url, StatuProcessItemTask.class);
		return status;
	}

	protected HttpHeaders getNecessaryHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}
}
