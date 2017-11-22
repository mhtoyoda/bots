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

import com.fiveware.config.ApiUrlPersistence;
import com.fiveware.model.ItemTask;
import com.fiveware.model.TaskFile;
import com.google.common.base.Strings;

@Service
public class ServiceTaskFile {

	static Logger logger = LoggerFactory.getLogger(ServiceTaskFile.class);

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ApiUrlPersistence apiUrlPersistence;


	public static boolean dataOutIsNotNullOrNotEmpty(ItemTask itemTask) {
		return !Strings.isNullOrEmpty(itemTask.getDataOut());
	}

	public TaskFile save(TaskFile itemFile) {
		String url = apiUrlPersistence.endPoint("task-file","");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<TaskFile> entity = new HttpEntity<TaskFile>(itemFile, headers);
		return restTemplate.postForObject(url, entity, TaskFile.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<TaskFile> getFileTaskById(Long id) {
		String url = apiUrlPersistence.endPoint("task-file/task/",id.toString());
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		List<TaskFile> itens = restTemplate.getForObject(url, List.class);
		return itens;
	}
}