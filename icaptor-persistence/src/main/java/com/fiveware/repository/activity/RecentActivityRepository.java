package com.fiveware.repository.activity;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fiveware.model.activity.RecentActivity;

@Repository
public interface RecentActivityRepository extends CrudRepository<RecentActivity, Long>{

	@Query("FROM RecentActivity WHERE task.id = :taskId")
	public List<RecentActivity> findAllByTaskId(Long taskId);
	
	@Query("FROM RecentActivity WHERE user.id = :userId")
	public List<RecentActivity> findAllByUserId(Long userId);


	
}
