package com.fiveware.service;

import java.util.List;

import com.fiveware.util.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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

	public List<RecentActivity> save(List<RecentActivity> activities) {
		String url = apiUrlPersistence.endPoint("recent-activities", "/new-activities");
		return restTemplate.exchange(url, HttpMethod.POST, UtilService.getCommonEntity(activities), getParameterizedList()).getBody();
	}

	public RecentActivity save(RecentActivity activity) {
		String url = apiUrlPersistence.endPoint("recent-activities", "/new-activity");
		return restTemplate.exchange(url, HttpMethod.POST, UtilService.getCommonEntity(activity), getParameterizedType()).getBody();
	}

	public void updateVisualedActivities(List<Long> activitiesIds) {
		String url = apiUrlPersistence.endPoint("recent-activities", "/mark-seen");
		restTemplate.exchange(url, HttpMethod.PUT, UtilService.getCommonEntity(activitiesIds), getParameterizedType()).getBody();
	}

	public List<RecentActivity> getAllNonVisualizedActivities(Long userId) {
		String url = apiUrlPersistence.endPoint("recent-activities", "/load-unseen/" + userId);
		return restTemplate.exchange(url, HttpMethod.GET, UtilService.getCommonEntity(null), getParameterizedList()).getBody();
	}

	public Long countNonSeenByUser(Long userId) {
		String url = apiUrlPersistence.endPoint("recent-activities", "/count-unseen/" + userId);
		return restTemplate.exchange(url, HttpMethod.GET, UtilService.getCommonEntity(null), Long.class).getBody();
	}

	public List<RecentActivity> findAllActivitiesForUser(Long userId) {
		String url = apiUrlPersistence.endPoint("recent-activities", "/user/" + userId);
		return restTemplate.exchange(url, HttpMethod.GET, UtilService.getCommonEntity(null), getParameterizedList()).getBody();
	}



	private ParameterizedTypeReference<List<RecentActivity>> getParameterizedList() {
		return new ParameterizedTypeReference<List<RecentActivity>>() {
		};
	}

	private ParameterizedTypeReference<RecentActivity> getParameterizedType() {
		return new ParameterizedTypeReference<RecentActivity>() {
		};
	}

}
