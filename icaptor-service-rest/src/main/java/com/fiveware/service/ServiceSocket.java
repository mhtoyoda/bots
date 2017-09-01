package com.fiveware.service;

import com.fiveware.model.Bot;
import com.fiveware.model.activity.RecentActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceSocket {

	private static final String BASE_URL = "http://localhost:8086";

	@Autowired
	private RestTemplate restTemplate;

	public void setPercent(Integer percent){

		String url = BASE_URL + "/percents/"+percent;

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		restTemplate.getForObject(url, Integer.class);
	}

}
