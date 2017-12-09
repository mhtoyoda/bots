package com.fiveware.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fiveware.controller.helper.WebModelUtil;
import com.fiveware.model.Bot;
import com.fiveware.model.StatusProcessTask;
import com.fiveware.model.Task;
import com.fiveware.model.TaskFile;
import com.fiveware.model.user.IcaptorUser;
import com.fiveware.security.util.SpringSecurityUtil;
import com.fiveware.service.ServiceBot;
import com.fiveware.service.ServiceItemTask;
import com.fiveware.service.ServiceStatusProcessTask;
import com.fiveware.service.ServiceTask;
import com.fiveware.service.ServiceTaskFile;
import com.fiveware.service.ServiceUser;

@EnableCaching
@RestController
@RequestMapping("/controlpanel")
public class ControlPanelController {

	private static final Logger logger = LoggerFactory.getLogger(ControlPanelController.class);

	@Autowired
	private ServiceStatusProcessTask statusTaskService;

	@Autowired
	private ServiceTask taskService;

	@Autowired
	private ServiceBot botService;

	@Autowired
	private ServiceUser userService;

	@Autowired
	private WebModelUtil modelHelper;

	@Autowired
	private ServiceItemTask serviceItemTask;
	
	@Autowired
	private ServiceTaskFile serviceTaskFile;

	@GetMapping("/loadTasks")
	@PreAuthorize("hasAuthority('ROLE_TASK_LIST')")
	public ResponseEntity<Object> loadTasks(@RequestHeader("Authorization") String details) {
		logger.debug("Loading all tasks for user [{}]", SpringSecurityUtil.decodeAuthorizationKey(details));

		Integer idUser = (Integer) SpringSecurityUtil.decodeAuthorizationKey(details, "idUser");

		List<Task> tasks = taskService.getTaskByUserIdOrderedByLoadTime(new Long(idUser));

		serviceItemTask.metric(tasks);

		return ResponseEntity.ok(tasks);
	}

	@GetMapping("/filter-tasks")
	@PreAuthorize("hasAuthority('ROLE_TASK_LIST')")
	public ResponseEntity<List<Task>> filterTast(@RequestParam(required = false) List<String> status, @RequestParam(required = false) String bot,
			@RequestParam(value = "initial_date", required = false) String initialDate, @RequestParam(value = "final_date", required = false) String finalDate,
			@RequestParam(required = false) List<String> usersId) {
		List<Task> tasks = taskService.searchForTaskWithFilters(status, bot, initialDate, finalDate, usersId);

		serviceItemTask.metric(tasks);

		return ResponseEntity.ok(tasks);
	}

		@Cacheable("status")
	@GetMapping("/tasks-filter")
	@PreAuthorize("hasAuthority('ROLE_TASK_LIST') and #oauth2.hasScope('read')")
	public ResponseEntity<Object> loadFilterOptions() {
		List<StatusProcessTask> allPossibleTaskStatus = statusTaskService.getAllPossibleTaskStatus();
		return ResponseEntity.ok(allPossibleTaskStatus);
	}

	@GetMapping("/bots-filter")
	public ResponseEntity<List<Map<String, Object>>> loadBotsFilterOptions() {
		List<Bot> bots = botService.findAll();
		List<Map<String, Object>> converted = modelHelper.convertBotsToMap(bots, "id", "nameBot");
		return ResponseEntity.ok(converted);
	}

	@GetMapping("/users-filter")
	public ResponseEntity<List<Map<String, Object>>> loadUsersFilterOptions() {
		List<IcaptorUser> users = userService.getAll();
		List<Map<String, Object>> converted = modelHelper.convertUsersToMap(users, "id", "name", "email");
		return ResponseEntity.ok(converted);
	}

	@PutMapping("/{taskId}/pause")
	@PreAuthorize("hasAuthority('ROLE_TASK_PAUSE') and #oauth2.hasScope('write')")
	public ResponseEntity<Object> pauseTask(@PathVariable Long taskId) {
		StatusProcessTask statusTask = new StatusProcessTask();
		statusTask.setId(7l);
		statusTask.setName("Suspending");

		changeTaskStatus(taskId, statusTask);

		return ResponseEntity.ok().build();
	}

	@PutMapping("/{taskId}/resume")
	@PreAuthorize("hasAuthority('ROLE_TASK_RESUME') and #oauth2.hasScope('write')")
	public ResponseEntity<Object> resumeTask(@PathVariable Long taskId) {
		StatusProcessTask statusTask = statusTaskService.getStatusProcessById(5l);

		changeTaskStatus(taskId, statusTask);

		return ResponseEntity.ok().build();
	}

	@PutMapping("/{taskId}/cancel")
	@PreAuthorize("hasAuthority('ROLE_TASK_CANCEL') and #oauth2.hasScope('write')")
	public ResponseEntity<Object> cancelTask(@PathVariable Long taskId) {
		StatusProcessTask statusTask = statusTaskService.getStatusProcessById(11l);

		changeTaskStatus(taskId, statusTask);

		return ResponseEntity.ok().build();
	}

	@GetMapping("/{idTask}/download")
	@PreAuthorize("hasAuthority('ROLE_TASK_LIST')")
	public String dowloand(@PathVariable Long idTask, HttpServletResponse response) {
		List<TaskFile> list = serviceTaskFile.getFileTaskById(idTask);
		byte[] fileZip = null;
		if(null != list && !list.isEmpty()){
			TaskFile taskFile = list.get(0);
			fileZip = taskFile.getFile();
		}
		try {
			response.setContentType("application/x-octet-stream");
			response.setHeader("Content-Disposition", "attachment;filename=Saida.zip");
			writeOut(response, fileZip);
			if(null != fileZip){
				response.setContentLength(fileZip.length);				
			}
		} catch (Exception e) {
			logger.error("{}", e);
		}

		return "OK";

	}

	protected void changeTaskStatus(Long taskId, StatusProcessTask status) {
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
			logger.error("Erro ao gravar bytes: {} ", e);
		}
	}
}
