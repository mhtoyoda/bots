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

import com.fiveware.model.ItemTask;
import com.fiveware.repository.ItemTaskRepository;

@RestController
@RequestMapping("/api/item/task")
public class ResourceItemTask {

	@Autowired
	private ItemTaskRepository itemTaskRepository;

	@GetMapping("/{id}")
	public ItemTask findOne(@PathVariable Long id) {
		return itemTaskRepository.findOne(id);
	}

	@GetMapping
	public ResponseEntity<Iterable<ItemTask>> findAll() {
		return ResponseEntity.ok(itemTaskRepository.findAll());
	}
	
	@PostMapping
	public ResponseEntity<?> save(@RequestBody ItemTask itemTask) {
		itemTask = itemTaskRepository.save(itemTask);
		return ResponseEntity.status(HttpStatus.CREATED).body(itemTask);
	}

	@GetMapping("/status/{status}")
	public ResponseEntity<List<ItemTask>> findByStatus(@PathVariable("status") String status) {
		return ResponseEntity.ok(itemTaskRepository.findItemTaskbyStatusProcess(status));
	}
}