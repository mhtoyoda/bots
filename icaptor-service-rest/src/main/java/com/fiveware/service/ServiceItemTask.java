package com.fiveware.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fiveware.model.ItemTask;

@Service
public class ServiceItemTask {

	static Logger logger = LoggerFactory.getLogger(ServiceItemTask.class);

	@Autowired
	private RestTemplate restTemplate;

	public ItemTask save(ItemTask item) {
		String url = "http://localhost:8085/api/item/task";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<ItemTask> entity = new HttpEntity<ItemTask>(item, headers);
		return restTemplate.postForObject(url, entity, ItemTask.class);
	}

	public List<ItemTask> getAll() {
		String url = "http://localhost:8085/api/item/task";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		List<ItemTask> itens = restTemplate.getForObject(url, List.class);
		return itens;
	}
	
	public ItemTask getItemTaskById(Long id) {
		String url = "http://localhost:8085/api/item/task/" +id;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		ItemTask item = restTemplate.getForObject(url, ItemTask.class);
		return item;
	}
	
	public List<ItemTask> getItemTaskByStatus(String status) {
		String url = "http://localhost:8085/api/item/task/status" +status;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		List<ItemTask> item = restTemplate.getForObject(url, List.class);
		return item;
	}
}
