package com.fiveware.service.activity;

import com.fiveware.model.activity.RecentActivity;
import com.fiveware.repository.activity.RecentActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceActivityImpl {

    @Autowired
    private RecentActivityRepository recentActivityRepository;

    public ServiceActivityImpl(RecentActivityRepository repository) {
        this.recentActivityRepository = repository;
    }

    public RecentActivity save(RecentActivity activity) {
        return recentActivityRepository.save(activity);
    }

    public void save(List<RecentActivity> activities) {
        activities.forEach(this::save);
    }

    public void setVisualized(List<Long> activityIds) {
        recentActivityRepository.setVisualized(activityIds);
    }

    public List<RecentActivity> findAllUnseenByUserId(Long userId) {
        List<RecentActivity> activities = recentActivityRepository.findAllUnseenByUserId(userId);
        return activities;
    }

    public Long countUnseenByUser(Long userId) {
        Long unseenByUser = recentActivityRepository.countUnseenByUser(userId);
        return unseenByUser;
    }

    public List<RecentActivity> getByUserId(Long userId) {
        List<RecentActivity> activities = recentActivityRepository.findAllByUserId(userId);
        return activities;
    }

    public List<RecentActivity> getByTaskId(Long taskId) {
        List<RecentActivity> activities = recentActivityRepository.findByTaskId(taskId);
        return activities;
    }

    public Iterable<RecentActivity> findAll() {
        Iterable<RecentActivity> activities = recentActivityRepository.findAll();
        return activities;
    }
}
