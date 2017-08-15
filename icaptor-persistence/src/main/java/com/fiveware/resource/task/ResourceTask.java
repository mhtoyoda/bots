package com.fiveware.resource.task;

import java.util.List;

import com.fiveware.model.StatuProcessTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}