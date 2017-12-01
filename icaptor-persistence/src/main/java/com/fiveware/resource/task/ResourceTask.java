package com.fiveware.resource.task;

import com.fiveware.model.Task;
import com.fiveware.repository.task.filter.TaskFilter;
import com.fiveware.service.task.ServiceTaskImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
public class ResourceTask {

	@Autowired
	private ServiceTaskImpl serviceTask;

	@PostMapping
	public ResponseEntity<?> save(@RequestBody Task task) {
		task = serviceTask.save(task);
		return ResponseEntity.status(HttpStatus.CREATED).body(task);
	}

	@PutMapping("/{id}/status")
	public ResponseEntity<?> update(@RequestBody Task task,@PathVariable Long id) {
		Task one = serviceTask.findOne(id);
		return ResponseEntity.status(HttpStatus.OK).body(one);
	}

	@GetMapping("/{id}")
	public Task findOne(@PathVariable Long id) {
		return serviceTask.findOne(id);
	}

	@GetMapping
	public List<Task> search(TaskFilter taskFilter) {
		return serviceTask.filter(taskFilter);
	}
	
	@GetMapping("/nameBot/{nameBot}")
	public ResponseEntity<List<Task>> findByBot(@PathVariable("nameBot") String nameBot) {
		return ResponseEntity.ok(serviceTask.findTaskbyBot(nameBot));
	}
	
	@GetMapping("/status/{status}")
	public ResponseEntity<List<Task>> findByStatus(@PathVariable("status") String status) {
		return ResponseEntity.ok(serviceTask.findTaskbyStatusProcess(status));
	}
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<Task>> findByUser(@PathVariable Long userId) {
		List<Task> tasks= serviceTask.findPeloUsuario(userId);
		return ResponseEntity.ok(tasks);
	}
	
	@GetMapping("/recent/status/{status}")
	public ResponseEntity<List<Task>> findRecentTaskByStatus(@PathVariable("status") String status) {		
		return ResponseEntity.ok(serviceTask.findRecentTaskByStatus(status));
	}
}