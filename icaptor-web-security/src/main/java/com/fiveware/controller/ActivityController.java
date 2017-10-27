package com.fiveware.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fiveware.model.activity.RecentActivity;
import com.fiveware.security.util.SpringSecurityUtil;
import com.fiveware.service.ServiceActivity;

@EnableCaching
@RestController
@RequestMapping("/activities")
public class ActivityController {

	@Autowired
	private ServiceActivity activityService;

	private static final Logger logger = LoggerFactory.getLogger(ActivityController.class);

	@GetMapping("/load")
	public ResponseEntity<Object> loadUnseenActivities(@RequestHeader("Authorization") String details) {
		List<RecentActivity> activities = activityService.findAllActivitiesForUser(getUserId(details));
		logger.debug("Loading [{}] activities for user [{}]", activities.size(), getUserId(details));
		return ResponseEntity.ok(activities);
	}

	@GetMapping("/count")
	public Long countUnseen(@RequestHeader("Authorization") String details) {
		Long nonSeenActivities = activityService.countNonSeenByUser(getUserId(details));
		logger.debug("Counted [{}] unseen activities for user [{}]", nonSeenActivities, getUserId(details));
		return nonSeenActivities;
	}

	@PutMapping("/mark-as-seen")
	@ResponseStatus(value = HttpStatus.OK)
	public void updateVisualizedActivities(@RequestBody List<Long> activitiesIds, @RequestHeader("Authorization") String details) {
		activityService.updateVisualedActivities(activitiesIds);
		logger.debug("Marked [{}] visualized activities for user [{}]", activitiesIds.size(), getUserId(details));
	}

	private Long getUserId(String details) {
		return Long.valueOf((Integer) SpringSecurityUtil.decodeAuthorizationKey(details, "idUser"));
	}

}
