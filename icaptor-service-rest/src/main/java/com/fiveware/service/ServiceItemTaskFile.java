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
import com.fiveware.model.ItemTaskFile;
import com.google.common.base.Strings;

@Service
public class ServiceItemTaskFile {

	static Logger logger = LoggerFactory.getLogger(ServiceItemTaskFile.class);

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ApiUrlPersistence apiUrlPersistence;


	public static boolean dataOutIsNotNullOrNotEmpty(ItemTask itemTask) {
		return !Strings.isNullOrEmpty(itemTask.getDataOut());
	}

	public ItemTaskFile save(ItemTaskFile itemFile) {
		String url = apiUrlPersistence.endPoint("item-task-file","");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<ItemTaskFile> entity = new HttpEntity<ItemTaskFile>(itemFile, headers);
		return restTemplate.postForObject(url, entity, ItemTaskFile.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<ItemTaskFile> getItemFileTaskById(Long id) {
		String url = apiUrlPersistence.endPoint("item-task-file/item-task/",id.toString());
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		List<ItemTaskFile> itens = restTemplate.getForObject(url, List.class);
		return itens;
	}
}