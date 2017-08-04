package com.fiveware.resource.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiveware.model.Task;
import com.fiveware.repository.TaskRepository;

@RestController
@RequestMapping("/api/task")
public class ResourceTask {

	@Autowired
	private TaskRepository taskRepository;

	@PostMapping
	public ResponseEntity<?> save(@RequestBody Task task) {
		task = taskRepository.save(task);
		return ResponseEntity.status(HttpStatus.CREATED).body(task);
	}

	@GetMapping("/{id}")
	public Task findOne(@PathVariable Long id) {
		return taskRepository.findOne(id);
	}

	@GetMapping
	public ResponseEntity<Iterable<Task>> findAll() {
		return ResponseEntity.ok(taskRepository.findAll());
	}

}