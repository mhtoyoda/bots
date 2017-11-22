package com.fiveware.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fiveware.service.ServiceBot;

@Component
public class ConsolidatedItemTaskFile {

	@Autowired
	private TaskManager taskManager;
	
	@Autowired
	private ServiceBot serviceBot;
	
}