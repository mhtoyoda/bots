package com.fiveware.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fiveware.config.ApiUrlPersistence;
import com.fiveware.model.BotsMetric;
import com.fiveware.model.ItemTask;
import com.fiveware.model.StatuProcessItemTask;
import com.fiveware.model.StatusProcessItemTaskEnum;
import com.fiveware.model.Task;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

@Service
public class ServiceItemTask {

	static Logger logger = LoggerFactory.getLogger(ServiceItemTask.class);

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ApiUrlPersistence apiUrlPersistence;


	public static boolean dataOutIsNotNullOrNotEmpty(ItemTask itemTask) {
		return !Strings.isNullOrEmpty(itemTask.getDataOut());
	}

	public ItemTask save(ItemTask item) {
		String url = apiUrlPersistence.endPoint("item-task","");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<ItemTask> entity = new HttpEntity<ItemTask>(item, headers);
		return restTemplate.postForObject(url, entity, ItemTask.class);
	}

	public List<ItemTask> getAll() {
		String url = apiUrlPersistence.endPoint("item-task","");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		List<ItemTask> itens = restTemplate.getForObject(url, List.class);
		return itens;
	}
	
	public ItemTask getItemTaskById(Long id) {
		String url = apiUrlPersistence.endPoint("item-task/",id.toString());

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		ItemTask item = restTemplate.getForObject(url, ItemTask.class);
		return item;
	}
	
	public ItemTask updateStatus(Long id, StatuProcessItemTask status) {
		String url = apiUrlPersistence.endPoint("item-task/",id+"/status");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		ItemTask itemTask = new ItemTask();
		itemTask.setStatusProcess(status);
		HttpEntity<ItemTask> entity = new HttpEntity<ItemTask>(itemTask, headers);

		ResponseEntity<ItemTask> exchange = restTemplate.exchange(url, HttpMethod.PUT, entity, ItemTask.class);
		return exchange.getBody();
	}

	public List<ItemTask> getItemTaskByStatus(String status) {
		String url = apiUrlPersistence.endPoint("item-task/","status/" +status);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<ItemTask> httpEntity = new HttpEntity<>(null, headers);
		ResponseEntity<List<ItemTask>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<List<ItemTask>>() {});
		List<ItemTask> item = responseEntity.getBody();
		return item;
	}
	
	public List<ItemTask> getItemTaskByListStatus(List<String> status, Long taskId) {
		String url = apiUrlPersistence.endPoint("item-task/",taskId+"/status");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<List<String>> httpEntity = new HttpEntity<>(status, headers);
		ResponseEntity<List<ItemTask>> exchange = restTemplate.exchange(url, HttpMethod.POST, (HttpEntity<?>) httpEntity, new ParameterizedTypeReference<List<ItemTask>>() {});
		List<ItemTask> item = exchange.getBody();
		return item;
	}

	public List<ItemTask> getItemTaskes(Long taskId) {
		String url = apiUrlPersistence.endPoint("item-task","/task/"+taskId);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		ResponseEntity<List<ItemTask>> exchange = restTemplate.exchange(url, HttpMethod.GET,null, new ParameterizedTypeReference<List<ItemTask>>() {});
		List<ItemTask> item = exchange.getBody();
		return item;
	}
	
	public Long getItemTaskCountByTask(Long taskId) {
		String url = apiUrlPersistence.endPoint("item-task/",taskId+"/count");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Long> httpEntity = new HttpEntity<>(null, headers);
		ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<Long>() {});
		Long count = responseEntity.getBody();
		return count;
	}


	public void metric(List<Task> tasks){
		tasks.stream().forEach(task ->{
			long errors = getItemTaskByListStatus(Lists.newArrayList(StatusProcessItemTaskEnum.ERROR.getName()), task.getId())
									.stream().count();

			long success = getItemTaskByListStatus(Lists.newArrayList(StatusProcessItemTaskEnum.SUCCESS.getName()), task.getId())
					.stream().count();

			Long itemTaskCountByTask = getItemTaskCountByTask(task.getId());

			BotsMetric metrics = new BotsMetric.BuilderBotsMetric()
					.addAmount(itemTaskCountByTask)
					.addError(errors)
					.addSuccess(success)
					.build();

			task.setBotsMetric(metrics);
		});

	}
}