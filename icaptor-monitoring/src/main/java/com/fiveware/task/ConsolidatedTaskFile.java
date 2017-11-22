package com.fiveware.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fiveware.service.ServiceBot;
import com.fiveware.service.ServiceTask;

@Component
public class ConsolidatedTaskFile {

	@Autowired
	private ServiceTask serviceTask;
	
	@Autowired
	private ServiceBot serviceBot;
	
	
}