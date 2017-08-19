package com.fiveware.resource.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

	@PutMapping("/{id}/status")
	public ResponseEntity<?> update(@RequestBody Task task,@PathVariable Long id) {
		Task one = taskRepository.findOne(id);
		one.setStatusProcess(task.getStatusProcess());
		taskRepository.save(one);
		return ResponseEntity.status(HttpStatus.OK).body(one);
	}

	@GetMapping("/{id}")
	public Task findOne(@PathVariable Long id) {
		return taskRepository.findOne(id);
	}

	@GetMapping
	public ResponseEntity<Iterable<Task>> findAll() {
		return ResponseEntity.ok(taskRepository.findAll());
	}
	
	@GetMapping("/nameBot/{nameBot}")
	public ResponseEntity<List<Task>> findByBot(@PathVariable("nameBot") String nameBot) {
		return ResponseEntity.ok(taskRepository.findTaskbyBot(nameBot));
	}
	
	@GetMapping("/status/{status}")
	public ResponseEntity<List<Task>> findByStatus(@PathVariable("status") String status) {
		return ResponseEntity.ok(taskRepository.findTaskbyStatusProcess(status));
	}
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<Task>> findByUser(@PathVariable Long userId) {
		List<Task> tasks= taskRepository.findPeloUsuario(userId);
		return ResponseEntity.ok(tasks);
	}
}