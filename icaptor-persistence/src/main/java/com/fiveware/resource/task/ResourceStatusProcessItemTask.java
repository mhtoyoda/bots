package com.fiveware.resource.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiveware.model.StatuProcessItemTask;
import com.fiveware.repository.StatuProcessItemTaskRepository;

@RestController
@RequestMapping("/api/item/status/")
public class ResourceStatusProcessItemTask {

	@Autowired
	private StatuProcessItemTaskRepository statuProcessItemTaskRepository;

	@GetMapping("/{id}")
	public StatuProcessItemTask findOne(@PathVariable Long id) {
		return statuProcessItemTaskRepository.findOne(id);
	}
	
}