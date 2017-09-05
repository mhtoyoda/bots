package com.fiveware.controller;

import com.fiveware.model.StatuProcessTask;
import com.fiveware.model.Task;
import com.fiveware.model.activity.RecentActivity;
import com.fiveware.security.SpringSecurityUtil;
import com.fiveware.service.ServiceActivity;
import com.fiveware.service.ServiceItemTask;
import com.fiveware.service.ServiceStatusProcessTask;
import com.fiveware.service.ServiceTask;
import com.fiveware.util.Zip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/controlpanel")
public class ControlPanelController {

	private static final Logger logger = LoggerFactory.getLogger(ControlPanelController.class);

	@Autowired
	private ServiceStatusProcessTask statusTaskService;

	@Autowired
	private ServiceTask taskService;

	@Autowired
	private ServiceItemTask serviceItemTask;

	@Autowired
	private ServiceActivity activityService;

	@Autowired
	private JwtAccessTokenConverter accessTokenConverter;

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
		StatuProcessTask statusTask = statusTaskService.getStatusProcessById(7l);

		statusTask.setName("Suspended");

		changeTaskStatus(taskId, statusTask);

		return ResponseEntity.ok().build();
	}

	@PutMapping("/resume")
	@PreAuthorize("hasAuthority('ROLE_TASK_RESUME') and #oauth2.hasScope('write')")
	public ResponseEntity<Object> resumeTask(@RequestBody Long taskId) {
		StatuProcessTask statusTask = statusTaskService.getStatusProcessById(5l);

		changeTaskStatus(taskId, statusTask);

		return ResponseEntity.ok().build();
	}

	@PutMapping("/cancel")
	@PreAuthorize("hasAuthority('ROLE_TASK_CANCEL') and #oauth2.hasScope('write')")
	public ResponseEntity<Object> cancelTask(@RequestBody Long taskId) {
		StatuProcessTask statusTask = statusTaskService.getStatusProcessById(10l);

		changeTaskStatus(taskId, statusTask);

		return ResponseEntity.ok().build();
	}

	@GetMapping("/download/{idTask}")
	@PreAuthorize("hasAuthority('ROLE_TASK_LIST')")
	public String dowloand(@PathVariable Long idTask, HttpServletResponse response) {

		List<byte[]> arrData = serviceItemTask.download(idTask)
				.stream()
				.map((itemTask) -> itemTask.getDataOut().getBytes())
				.collect(Collectors.toList());

		try {
			byte[] fileZip = Zip.zipBytes("saida.txt", arrData);


		response.setContentType("application/x-octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename=Entrada.zip");

		writeOut(response, fileZip);

		response.setContentLength(fileZip.length);

		} catch (IOException e) {
			logger.error("{}",e);
		}

		return "OK";


	}

	protected void changeTaskStatus(Long taskId, StatuProcessTask status) {
		taskService.updateStatus(taskId, status);
		logger.debug("Task [{}] status updated to [{}]", taskId, status.getName());
	}

	private void writeOut(HttpServletResponse response, byte[] arrData) {
		try {
			ServletOutputStream outStream = response.getOutputStream();

			outStream.write(arrData, 0, arrData.length);
			outStream.flush();
			outStream.close();
		} catch (IOException e) {
			logger.error("Erro ao gravar bytes: {} ",e);
		}
	}
}
