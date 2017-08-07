package com.fiveware.resource.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiveware.model.StatuProcess;
import com.fiveware.repository.StatuProcessRepository;

@RestController
@RequestMapping("/api/status")
public class ResourceStatusProcessTask {

	@Autowired
	private StatuProcessRepository statuProcessRepository;

	@GetMapping("/{id}")
	public StatuProcess findOne(@PathVariable Long id) {
		return statuProcessRepository.findOne(id);
	}
	
}