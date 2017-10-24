package com.fiveware.resource.activity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

	@PostMapping("/new-activity")
	@ResponseStatus(code = HttpStatus.CREATED)
	public void save(@RequestBody RecentActivity activity) {
		repository.save(activity);
	}

	@PostMapping("/new-activities")
	@ResponseStatus(code = HttpStatus.CREATED)
	public void save(@RequestBody List<RecentActivity> activities) {
		repository.save(activities);
	}
	
	@PutMapping("/mark-seen")
	@ResponseStatus(code = HttpStatus.OK)
	public void updateVisualized(@RequestBody List<Long> activityIds) {
		repository.setVisualized(activityIds);
	}

	@GetMapping("/load-unseen/{userId}")
	public ResponseEntity<List<RecentActivity>> getUnseenByUserId(@PathVariable Long userId) {
		List<RecentActivity> activities = repository.findAllUnseenByUserId(userId);
		return ResponseEntity.ok(activities);
	}
	
	@GetMapping("/count-unseen/{userId}")
	public ResponseEntity<Long> countUnseenByUserId(@PathVariable Long userId) {
		Long unseenByUser = repository.countUnseenByUser(userId);
		return ResponseEntity.ok(unseenByUser);
	}
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<RecentActivity>> getByUserId(@PathVariable Long userId) {
		List<RecentActivity> activities = repository.findAllByUserId(userId);
		return ResponseEntity.ok(activities);
	}

	@GetMapping("/task/{taskId}")
	public ResponseEntity<List<RecentActivity>> getByTaskId(@PathVariable Long taskId) {
		List<RecentActivity> activities = repository.findAllByUserId(taskId);
		return ResponseEntity.ok(activities);
	}
	
}
