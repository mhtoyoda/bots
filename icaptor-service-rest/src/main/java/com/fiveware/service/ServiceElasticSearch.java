package com.fiveware.service;

import com.fiveware.config.ApiUrlPersistence;
import com.fiveware.model.Bot;
import com.fiveware.model.ItemTask;
import com.fiveware.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceElasticSearch {

	private static Logger logger = LoggerFactory.getLogger(ServiceElasticSearch.class);


	@Autowired
	private ApiUrlPersistence apiUrlPersistence;

	@Autowired
	private RestTemplate restTemplate;

	public void indexer(Task task) {

			String url = apiUrlPersistence.endPointElasticSearch("icaptor-automation","/task/"+task.getId());

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<Task> entity = new HttpEntity<Task>(task, headers);
			restTemplate.postForEntity(url, entity, Task.class);
	}

	public void indexer(ItemTask itemTask) {

			String url = apiUrlPersistence.endPointElasticSearch("icaptor-automation", "/item-task/" + itemTask.getId());

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<ItemTask> entity = new HttpEntity<ItemTask>(itemTask, headers);
			restTemplate.postForEntity(url, entity, ItemTask.class);
	}

}