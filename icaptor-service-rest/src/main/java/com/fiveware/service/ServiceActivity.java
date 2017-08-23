package com.fiveware.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fiveware.model.activity.RecentActivity;

@Service
public class ServiceActivity {

	private static final String BASE_URL = "http://localhost:8085/api/recent-activities";

	@Autowired
	private RestTemplate restTemplate;

	public RecentActivity save(RecentActivity activity) {
		String url = BASE_URL + "/new";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<RecentActivity> entity = new HttpEntity<RecentActivity>(activity, headers);
		return restTemplate.postForObject(url, entity, RecentActivity.class);
	}

	public List<RecentActivity> findByUserId(Long userId) {
		String url = BASE_URL + "/user/" + userId;
		return restTemplate.getForObject(url, List.class);
	}

	public List<RecentActivity> findByTaskId(Long taskId) {
		String url = BASE_URL + "/task/" + taskId;
		return restTemplate.getForObject(url, List.class);
	}

}
