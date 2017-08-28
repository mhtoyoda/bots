package com.fiveware.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiveware.model.StatuProcessTask;
import com.fiveware.model.Task;
import com.fiveware.model.activity.RecentActivity;
import com.fiveware.security.SpringSecurityUtil;
import com.fiveware.service.ServiceActivity;
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

	@Autowired
	private ServiceActivity activityService;

	@GetMapping("/loadTasks/user/{id}")
	@PreAuthorize("hasAuthority('ROLE_TASK_LIST')")
	public ResponseEntity<Object> loadTasks(@PathVariable Long id, @RequestHeader("Authorization") String details) {
		logger.debug("Loading all tasks for user [{}]", SpringSecurityUtil.decodeAuthorizationKey(details));
		List<Task> tasks = taskService.getTaskByUserIdOrderedByLoadTime(id);
		return ResponseEntity.ok(tasks);
	}

	@GetMapping("/recent-activities/user/{id}")
	public ResponseEntity<Object> loadRecentActivities(@PathVariable Long id) {
		List<RecentActivity> activities = activityService.findByUserId(id);
		return ResponseEntity.ok(activities);
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
