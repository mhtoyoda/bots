package com.fiveware.resource.activity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fiveware.model.activity.RecentActivity;
import com.fiveware.repository.activity.RecentActivityRepository;

@RestController
@RequestMapping("/api/recent-activities")
public class ResourceRecentActivity {

	@Autowired
	private RecentActivityRepository repository;

	@PostMapping("/new")
	@ResponseStatus(code = HttpStatus.CREATED)
	public void save(@RequestBody RecentActivity activity) {
		repository.save(activity);
	}

	@GetMapping("/user/{id}")
	public ResponseEntity<List<RecentActivity>> getByUserId(@PathVariable Long userId) {
		List<RecentActivity> activities = repository.findAllByUserId(userId);
		return ResponseEntity.ok(activities);
	}

	@GetMapping("/task/{id}")
	public ResponseEntity<List<RecentActivity>> getByTaskId(@PathVariable Long taskId) {
		List<RecentActivity> activities = repository.findAllByUserId(taskId);
		return ResponseEntity.ok(activities);
	}

}
