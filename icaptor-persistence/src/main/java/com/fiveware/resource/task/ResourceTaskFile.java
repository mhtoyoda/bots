package com.fiveware.resource.task;

import java.util.List;

import com.fiveware.service.task.ServiceTaskFileImpl;
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

import com.fiveware.model.TaskFile;

@RestController
@RequestMapping("/api/task-file")
public class ResourceTaskFile {

	@Autowired
	private ServiceTaskFileImpl serviceTaskFileImpl;

	@PostMapping
	public ResponseEntity<?> save(@RequestBody TaskFile _taskFile) {
		TaskFile taskFile = serviceTaskFileImpl.save(_taskFile);
		return ResponseEntity.status(HttpStatus.CREATED).body(taskFile);
	}

	@GetMapping("/task/{id}")
	public ResponseEntity<List<TaskFile>> findAllByTask(@PathVariable Long id) {
		return ResponseEntity.ok(serviceTaskFileImpl.findFilebyTaskId(id));
	}
	
	@DeleteMapping
	public ResponseEntity<Void> delete(@RequestBody TaskFile taskFile) {
		serviceTaskFileImpl.delete(taskFile);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}