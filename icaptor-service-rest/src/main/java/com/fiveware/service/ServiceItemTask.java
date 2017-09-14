package com.fiveware.service;

import com.fiveware.config.ApiUrlPersistence;
import com.fiveware.model.*;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ServiceItemTask {

	static Logger logger = LoggerFactory.getLogger(ServiceItemTask.class);

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ApiUrlPersistence apiUrlPersistence;




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
		ResponseEntity<List<ItemTask>> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, new ParameterizedTypeReference<List<ItemTask>>() {});
		List<ItemTask> item = responseEntity.getBody();
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

	public List<ItemTask> download(Long idTask) {
		return getItemTaskByListStatus(Lists.newArrayList(StatusProcessTaskEnum.PROCESSED.getName()),idTask);
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
