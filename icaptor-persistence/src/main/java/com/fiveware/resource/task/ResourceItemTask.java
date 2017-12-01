package com.fiveware.resource.task;

import com.fiveware.model.ItemTask;
import com.fiveware.service.task.ServiceItemTaskImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/item-task")
public class ResourceItemTask {

	@Autowired
	private ServiceItemTaskImpl serviceItemTask;
	
	@GetMapping("/{id}")
	public ItemTask findOne(@PathVariable Long id) {
		return serviceItemTask.findOne(id);
	}

	@GetMapping
	public ResponseEntity<Iterable<ItemTask>> findAll() {
		return ResponseEntity.ok(serviceItemTask.findAll());
	}

	@GetMapping("/task/{id}")
	public ResponseEntity<List<ItemTask>> findAllByTask(@PathVariable Long id) {
		return ResponseEntity.ok(serviceItemTask.findAllByTask(id));
	}

	@PostMapping
	public ResponseEntity<?> save(@RequestBody ItemTask itemTask) {
		itemTask = serviceItemTask.save(itemTask);
		return ResponseEntity.status(HttpStatus.CREATED).body(itemTask);
	}

	@PutMapping("/{id}/status")
	public ResponseEntity<?> update(@RequestBody ItemTask paramItemTask,@PathVariable Long id) {
		ItemTask itemTask = serviceItemTask.update(paramItemTask,id);
		return ResponseEntity.ok().body(itemTask);
	}

	@GetMapping("/status/{status}")
	public ResponseEntity<List<ItemTask>> findByStatus(@PathVariable("status") String status) {
		return ResponseEntity.ok(serviceItemTask.findByStatus(status));
	}
	
	@PostMapping("/{taskId}/status")
	public ResponseEntity<List<ItemTask>> findItemTaskbyListStatusProcess(@PathVariable("taskId") Long taskId, @RequestBody List<String> status) {
		return ResponseEntity.ok(serviceItemTask.findItemTaskbyListStatusProcess(taskId, status));
	}
	
	@GetMapping("/{taskId}/count")
	public ResponseEntity<Long> findItemTaskbyListStatusProcess(@PathVariable("taskId") Long taskId) {
		return ResponseEntity.ok(serviceItemTask.countItemTask(taskId));
	}
}