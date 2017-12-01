package com.fiveware.resource.task;

import com.fiveware.service.task.ServiceStatusProcessImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiveware.model.StatusProcessItemTask;

@RestController
@RequestMapping("/api/item/status/")
public class ResourceStatusProcessItemTask {

	@Autowired
	private ServiceStatusProcessImpl serviceStatusProcess;

	@GetMapping("/{id}")
	public StatusProcessItemTask findOne(@PathVariable Long id) {
		return serviceStatusProcess.getStatusProcessItemTask(id);
	}
	
}