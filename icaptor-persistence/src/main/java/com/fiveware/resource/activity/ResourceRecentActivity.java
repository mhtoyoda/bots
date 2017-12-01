package com.fiveware.resource.activity;

import java.util.List;

import com.fiveware.exception.ApiError;
import com.fiveware.service.activity.ServiceActivityImpl;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fiveware.model.activity.RecentActivity;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

@RestController
@RequestMapping("/api/recent-activities")
public class ResourceRecentActivity {

	@Autowired
	private final ServiceActivityImpl serviceActivity;

	public ResourceRecentActivity(ServiceActivityImpl serviceActivity) {
		this.serviceActivity = serviceActivity;
	}

	@PostMapping("/new-activity")
	public ResponseEntity<?> save(@RequestBody RecentActivity activity) {
		try {
			RecentActivity save = serviceActivity.save(activity);
			return ResponseEntity.status(HttpStatus.CREATED).body(save);
		}catch (InvalidDataAccessApiUsageException e){
			throw e;
		}
	}

	@PostMapping("/new-activities")
	@ResponseStatus(code = HttpStatus.CREATED)
	public void save(@RequestBody List<RecentActivity> activities) {
		serviceActivity.save(activities);
	}
	
	@PutMapping("/mark-seen")
	@ResponseStatus(code = HttpStatus.OK)
	public void updateVisualized(@RequestBody List<Long> activityIds) {
		serviceActivity.setVisualized(activityIds);
	}

	@GetMapping("/load-unseen/{userId}")
	public List<RecentActivity> getUnseenByUserId(@PathVariable Long userId) {
		List<RecentActivity> activities = serviceActivity.findAllUnseenByUserId(userId);
		return activities;
	}
	
	@GetMapping("/count-unseen/{userId}")
	public ResponseEntity<Long> countUnseenByUserId(@PathVariable Long userId) {
		Long unseenByUser = serviceActivity.countUnseenByUser(userId);
		return ResponseEntity.ok(unseenByUser);
	}
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<RecentActivity>> getByUserId(@PathVariable Long userId) {
		List<RecentActivity> activities = serviceActivity.getByUserId(userId);
		return ResponseEntity.ok(activities);
	}

	@GetMapping("/task/{taskId}")
	public ResponseEntity<List<RecentActivity>> getByTaskId(@PathVariable Long taskId) {
		List<RecentActivity> activities = serviceActivity.getByTaskId(taskId);
		return ResponseEntity.ok(activities);
	}


	@ExceptionHandler({ InvalidDataAccessApiUsageException.class })
	public ResponseEntity<Object> handleInvalidDataAccessApiUsageException(
			InvalidDataAccessApiUsageException ex, WebRequest request) {
		String error = ex.getCause().getMessage();

		ApiError apiError =
				new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<Object> handleConstraintViolation(
			ConstraintViolationException ex, WebRequest request) {
		List<String> errors = Lists.newArrayList();
		for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
			errors.add(violation.getMessage());
		}

		ApiError apiError =
				new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}


}
