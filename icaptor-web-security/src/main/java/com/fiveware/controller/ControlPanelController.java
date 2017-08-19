package com.fiveware.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiveware.model.StatuProcessTask;
import com.fiveware.model.Task;
import com.fiveware.security.IcaptorUserDetail;
import com.fiveware.service.ServiceStatusProcessTask;
import com.fiveware.service.ServiceTask;


@RestController
@RequestMapping("/controlpanel")
public class ControlPanelController {

	private static final Logger logger = LoggerFactory.getLogger(ControlPanelController.class);

	@Autowired
	private ServiceStatusProcessTask statusTaskService;

	@Autowired
	private ServiceTask taskService;
	
	@GetMapping("/loadTasks")
	public ResponseEntity<Object> loadTasks(@AuthenticationPrincipal IcaptorUserDetail userDetail) {
		logger.info("Loading all tasks for user [{}]", userDetail.getUsername());
		List<Task> tasks = taskService.getTaskByUserIdOrderedByLoadTime(userDetail.getUserId());
		return ResponseEntity.ok(tasks);
	}

	@GetMapping("/loadRecentActivities")
	public ResponseEntity<Object> loadRecentActivities() {
		// TODO: Load recent activities
		return ResponseEntity.ok().build();
	}

	@GetMapping("/loadFilterOptions")
	public ResponseEntity<Object> loadFilterOptions() {
		List<StatuProcessTask> allPossibleTaskStatus = statusTaskService.getAllPossibleTaskStatus();
		// TODO: Load all avaible bots for a user.
		// TODO: Load all users if this user have privileges.
		return ResponseEntity.ok(allPossibleTaskStatus);
	}

	@PutMapping("/pause")
	public ResponseEntity<Object> pauseTask(@RequestBody Long taskId) {
		StatuProcessTask statusTask = new StatuProcessTask();
		statusTask.setId(7l);
		statusTask.setName("Suspended");

		changeTaskStatus(taskId, statusTask);
		
		return ResponseEntity.ok().build();
	}

	@PutMapping("/resume")
	public ResponseEntity<Object> resumeTask(@RequestBody Long taskId) {
		StatuProcessTask statusTask = new StatuProcessTask();
		statusTask.setId(5l);
		statusTask.setName("Processing");

		changeTaskStatus(taskId, statusTask);
		
		return ResponseEntity.ok().build();
	}

	@PutMapping("/cancel")
	public ResponseEntity<Object> cancelTask(@RequestBody Long taskId) {
		StatuProcessTask statusTask = new StatuProcessTask();
		statusTask.setId(10l);
		statusTask.setName("Canceled");
		
		changeTaskStatus(taskId, statusTask);
		
		return ResponseEntity.ok().build();
	}
	
	protected void changeTaskStatus(Long taskId, StatuProcessTask status) {
		taskService.updateStatus(taskId, status);
		logger.debug("Task [{}] status updated to [{}]", taskId, status.getName());
	}
}
