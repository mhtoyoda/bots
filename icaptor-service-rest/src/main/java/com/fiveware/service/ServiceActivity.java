package com.fiveware.service;

import com.fiveware.config.ApiUrlPersistence;
import com.fiveware.model.activity.RecentActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ServiceActivity {

	@Autowired
	private ApiUrlPersistence apiUrlPersistence;

	@Autowired
	private RestTemplate restTemplate;

	public RecentActivity save(RecentActivity activity) {
		String url = apiUrlPersistence.endPoint("recent-activities","/new");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<RecentActivity> entity = new HttpEntity<RecentActivity>(activity, headers);
		return restTemplate.postForObject(url, entity, RecentActivity.class);
	}

	public List<RecentActivity> findByUserId(Long userId) {
		String url = apiUrlPersistence.endPoint("recent-activities","/user/" + userId);
		return restTemplate.getForObject(url, List.class);
	}

	public List<RecentActivity> findByTaskId(Long taskId) {
		String url = apiUrlPersistence.endPoint("recent-activities","/task/" + taskId);
		return restTemplate.getForObject(url, List.class);
	}



}
