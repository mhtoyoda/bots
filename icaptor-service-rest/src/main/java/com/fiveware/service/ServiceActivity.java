package com.fiveware.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fiveware.config.ApiUrlPersistence;
import com.fiveware.model.activity.RecentActivity;

@Service
public class ServiceActivity {

	@Autowired
	private ApiUrlPersistence apiUrlPersistence;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private RestTemplate restTemplate;

	public RecentActivity save(String messageSourceName, Object[] messagePrams, Long userId) {
		String message = messageSource.getMessage(messageSourceName, messagePrams, LocaleContextHolder.getLocale());
		return this.save(new RecentActivity(message, userId));
	}
	
	public List<RecentActivity> save(List<RecentActivity> activities){
		String url = apiUrlPersistence.endPoint("recent-activities", "/new-activities");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<List<RecentActivity>> entity = new HttpEntity<List<RecentActivity>>(activities, headers);
		ResponseEntity<List<RecentActivity>> response = restTemplate.exchange(url, HttpMethod.POST, entity, new ParameterizedTypeReference<List<RecentActivity>>() {
		});
		return response.getBody();
	}

	public RecentActivity save(RecentActivity activity) {
		String url = apiUrlPersistence.endPoint("recent-activities", "/new-activity");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<RecentActivity> entity = new HttpEntity<RecentActivity>(activity, headers);
		return restTemplate.postForObject(url, entity, RecentActivity.class);
	}


	public List<RecentActivity> findByUserId(Long userId) {
		String url = apiUrlPersistence.endPoint("recent-activities", "/user/" + userId);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Object> entity = new HttpEntity<>(headers);
		
		ResponseEntity<List<RecentActivity>> response = restTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<List<RecentActivity>>() {
		});
		return response.getBody();
	}

	public List<RecentActivity> findByTaskId(Long taskId) {
		String url = apiUrlPersistence.endPoint("recent-activities", "/task/" + taskId);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Object> entity = new HttpEntity<>(headers);

		ResponseEntity<List<RecentActivity>> response = restTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<List<RecentActivity>>() {
		});
		return response.getBody();
	}

	
	public List<RecentActivity> updateVisualedActivities(){
		String url = apiUrlPersistence.endPoint("recent-activities", "/new-activities");
		return null;
	}

}
