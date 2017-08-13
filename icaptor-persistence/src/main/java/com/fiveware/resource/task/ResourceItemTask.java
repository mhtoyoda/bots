package com.fiveware.resource.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

	@PutMapping("/{id}/status")
	public ResponseEntity<?> update(@RequestBody ItemTask paramItemTask,@PathVariable Long id) {
		ItemTask itemTask = itemTaskRepository.findOne(id);
		itemTask.setStatusProcess(paramItemTask.getStatusProcess());
		itemTaskRepository.save(itemTask);
		return ResponseEntity.ok().body(itemTask);
	}

	@GetMapping("/status/{status}")
	public ResponseEntity<List<ItemTask>> findByStatus(@PathVariable("status") String status) {
		return ResponseEntity.ok(itemTaskRepository.findItemTaskbyStatusProcess(status));
	}
}