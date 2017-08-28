package com.fiveware.repository.activity;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fiveware.model.activity.RecentActivity;

@Repository
public interface RecentActivityRepository extends CrudRepository<RecentActivity, Long>{

	@Query("FROM RecentActivity WHERE task.id = :taskId ORDER BY creation_time DESC ")
	public List<RecentActivity> findAllByTaskId(@Param("taskId") Long taskId);
	
	@Query("FROM RecentActivity WHERE user.id = :userId ORDER BY creation_time DESC")
	public List<RecentActivity> findAllByUserId(@Param("userId") Long userId);


	
}
