package com.fiveware.controller;

import com.fiveware.model.StatuProcessTask;
import com.fiveware.model.Task;
import com.fiveware.service.ServiceStatusProcessTask;
import com.fiveware.service.ServiceTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@EnableOAuth2Sso
@RequestMapping("/controlpanel")
public class ControlPanelController {

	private static final Logger logger = LoggerFactory.getLogger(ControlPanelController.class);

	@Autowired
	private ServiceStatusProcessTask statusTaskService;

	@Autowired
	private ServiceTask taskService;
	
	@GetMapping("/loadTasks/{id}/user")
	@PreAuthorize("hasAuthority('ROLE_TASK_LIST')")
	public ResponseEntity<Object> loadTasks(@PathVariable Long id) {
		logger.info("Loading all tasks for user [{}]", id);
		List<Task> tasks = taskService.getTaskByUserIdOrderedByLoadTime(id);
		return ResponseEntity.ok(tasks);
	}

	@GetMapping("/loadRecentActivities")
	public ResponseEntity<Object> loadRecentActivities() {
		// TODO: Load recent activities
		return ResponseEntity.ok().build();
	}

	@GetMapping("/loadFilterOptions")
	@PreAuthorize("hasAuthority('ROLE_TASK_LIST') and #oauth2.hasScope('read')")
	public ResponseEntity<Object> loadFilterOptions() {
		List<StatuProcessTask> allPossibleTaskStatus = statusTaskService.getAllPossibleTaskStatus();
		// TODO: Load all avaible bots for a user.
		// TODO: Load all users if this user have privileges.
		return ResponseEntity.ok(allPossibleTaskStatus);
	}

	@PutMapping("/pause")
	@PreAuthorize("hasAuthority('ROLE_TASK_PAUSE') and #oauth2.hasScope('write')")
	public ResponseEntity<Object> pauseTask(@RequestBody Long taskId) {
		StatuProcessTask statusTask = new StatuProcessTask();
		statusTask.setId(7l);
		statusTask.setName("Suspended");

		changeTaskStatus(taskId, statusTask);
		
		return ResponseEntity.ok().build();
	}

	@PutMapping("/resume")
	@PreAuthorize("hasAuthority('ROLE_TASK_RESUME') and #oauth2.hasScope('write')")
	public ResponseEntity<Object> resumeTask(@RequestBody Long taskId) {
		StatuProcessTask statusTask = new StatuProcessTask();
		statusTask.setId(5l);
		statusTask.setName("Processing");
		changeTaskStatus(taskId, statusTask);
		
		return ResponseEntity.ok().build();
	}

	@PutMapping("/cancel")
	@PreAuthorize("hasAuthority('ROLE_TASK_CANCEL') and #oauth2.hasScope('write')")
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
