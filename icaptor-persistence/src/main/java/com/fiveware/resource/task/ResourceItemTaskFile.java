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

import com.fiveware.model.ItemTaskFile;
import com.fiveware.repository.ItemTaskFileRepository;

@RestController
@RequestMapping("/api/item-task-file")
public class ResourceItemTaskFile {

	@Autowired
	private ItemTaskFileRepository itemTaskFileRepository;

	@PostMapping
	public ResponseEntity<?> save(@RequestBody ItemTaskFile itemTaskFile) {
		itemTaskFile = itemTaskFileRepository.save(itemTaskFile);
		return ResponseEntity.status(HttpStatus.CREATED).body(itemTaskFile);
	}

	@GetMapping("/item-task/{id}")
	public ResponseEntity<List<ItemTaskFile>> findAllByTask(@PathVariable Long id) {
		return ResponseEntity.ok(itemTaskFileRepository.findFilebyItemTaskId(id));
	}
}