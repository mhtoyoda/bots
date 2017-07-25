package com.fiveware.service.task;

import java.util.List;

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
public class ServiceTaskImpl implements IServiceTask {

	@Autowired
	private TaskRepository taskRepository;

	@Override
	@PostMapping("/save")
	public ResponseEntity<?> save(@RequestBody Task task) {
		task = taskRepository.save(task);
		return ResponseEntity.status(HttpStatus.CREATED).body(task);
	}

	@Override
	@GetMapping("/id/{id}")
	public Task findOne(@PathVariable("id") Long id) {
		return taskRepository.findOne(id);
	}

	@Override
	@GetMapping("/bot/{nameBot}/status/{status}")
	public List<Task> getAllTaskByStatus(@PathVariable("nameBot") String nameBot, @PathVariable("status") String status) {
		return taskRepository.getAllTaskBotByStatus(nameBot, status);
	}
}