package com.fiveware.resource.task;

import com.fiveware.service.task.ServiceStatusProcessImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiveware.model.StatusProcessTask;

@RestController
@RequestMapping("/api/status")
public class ResourceStatusProcessTask {

	@Autowired
	private ServiceStatusProcessImpl statuProcessRepository;

	@GetMapping("/{id}")
	public StatusProcessTask findOne(@PathVariable Long id) {
		return statuProcessRepository.getStatusProcessTask(id);
	}
	
	@GetMapping
	public ResponseEntity<Iterable<StatusProcessTask>> list() {
		return ResponseEntity.ok(statuProcessRepository.list());
	}
	
}