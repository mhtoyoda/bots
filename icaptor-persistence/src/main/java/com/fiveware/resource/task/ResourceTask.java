package com.fiveware.resource.task;

import com.fiveware.model.Task;
import com.fiveware.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

	@GetMapping("/bot/{nameBot}/status/{status}")
	public List<Task> getAllTaskByStatus(@PathVariable("nameBot") String nameBot, @PathVariable("status") String status) {
		return taskRepository.getAllTaskBotByStatus(nameBot, status);
	}
}