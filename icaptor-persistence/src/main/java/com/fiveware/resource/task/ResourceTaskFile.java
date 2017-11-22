package com.fiveware.resource.task;

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

import com.fiveware.model.TaskFile;
import com.fiveware.repository.TaskFileRepository;

@RestController
@RequestMapping("/api/task-file")
public class ResourceTaskFile {

	@Autowired
	private TaskFileRepository taskFileRepository;

	@PostMapping
	public ResponseEntity<?> save(@RequestBody TaskFile taskFile) {
		taskFile = taskFileRepository.save(taskFile);
		return ResponseEntity.status(HttpStatus.CREATED).body(taskFile);
	}

	@GetMapping("/task/{id}")
	public ResponseEntity<List<TaskFile>> findAllByTask(@PathVariable Long id) {
		return ResponseEntity.ok(taskFileRepository.findFilebyTaskId(id));
	}
}