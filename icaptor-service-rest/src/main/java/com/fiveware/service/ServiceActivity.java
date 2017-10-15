package com.fiveware.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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

	public RecentActivity save(RecentActivity activity) {
		String url = apiUrlPersistence.endPoint("recent-activities", "/new");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<RecentActivity> entity = new HttpEntity<RecentActivity>(activity, headers);
		return restTemplate.postForObject(url, entity, RecentActivity.class);
	}

	public List<RecentActivity> findByUserId(Long userId) {
		String url = apiUrlPersistence.endPoint("recent-activities", "/user/" + userId);
		return restTemplate.getForObject(url, List.class);
	}

	public List<RecentActivity> findByTaskId(Long taskId) {
		String url = apiUrlPersistence.endPoint("recent-activities", "/task/" + taskId);
		return restTemplate.getForObject(url, List.class);
	}

}
