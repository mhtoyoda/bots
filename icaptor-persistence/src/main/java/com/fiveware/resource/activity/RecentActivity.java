package com.fiveware.resource.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiveware.repository.activity.RecentActivityRepository;

@RestController
@RequestMapping("/api/recent-activities")
public class RecentActivity {

	@Autowired
	private RecentActivityRepository activityRepository;
}
