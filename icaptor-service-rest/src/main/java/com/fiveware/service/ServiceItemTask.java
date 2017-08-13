package com.fiveware.service;

import java.util.List;

import com.fiveware.model.StatuProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
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
	
	public ItemTask updateStatus(Long id,StatuProcess status) {
		String url = "http://localhost:8085/api/item/task/" +id+"/status";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		ItemTask itemTask = new ItemTask();
		itemTask.setStatusProcess(status);
		HttpEntity<ItemTask> entity = new HttpEntity<ItemTask>(itemTask, headers);

		ResponseEntity<ItemTask> exchange = restTemplate.exchange(url, HttpMethod.PUT, entity, ItemTask.class);
		return exchange.getBody();
	}
}
