package com.fiveware.resource.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiveware.model.Task;
import com.fiveware.model.TaskFile;
import com.fiveware.repository.TaskFileRepository;
import com.fiveware.repository.TaskRepository;

@RestController
@RequestMapping("/api/task-file")
public class ResourceTaskFile {

	@Autowired
	private TaskFileRepository taskFileRepository;
	
	@Autowired
	private TaskRepository taskRepository;

	@PostMapping
	public ResponseEntity<?> save(@RequestBody TaskFile taskFile) {
		Task task = taskRepository.findOne(taskFile.getTask().getId());
		taskFile.setTask(task);
		taskFile = taskFileRepository.save(taskFile);
		return ResponseEntity.status(HttpStatus.CREATED).body(taskFile);
	}

	@GetMapping("/task/{id}")
	public ResponseEntity<List<TaskFile>> findAllByTask(@PathVariable Long id) {
		return ResponseEntity.ok(taskFileRepository.findFilebyTaskId(id));
	}
	
	@DeleteMapping
	public ResponseEntity<Void> delete(@RequestBody TaskFile taskFile) {
		taskFile = taskFileRepository.findOne(taskFile.getId());
    	taskFileRepository.delete(taskFile);		
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}