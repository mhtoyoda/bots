package com.fiveware.repository.activity;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fiveware.model.activity.RecentActivity;

@Repository
public interface RecentActivityRepository extends CrudRepository<RecentActivity, Long> {

	@Query("FROM RecentActivity WHERE user.id = :userId ORDER BY creation_time DESC")
	public List<RecentActivity> findAllByUserId(@Param("userId") Long userId);

	@Query("FROM RecentActivity WHERE user.id = :userId AND visualized = false")
	public List<RecentActivity> findAllUnseenByUserId(@Param("userId") Long userId);
	
	@Query("SELECT COUNT(*) FROM RecentActivity WHERE user.id = :userId AND visualized = false")
	public Long countUnseenByUser(@Param("userId") Long userId);
	
	@Modifying
	@Transactional
	@Query("UPDATE RecentActivity SET visualized = true WHERE id IN (:ids)")
	void setVisualized(@Param("ids") List<Long> activityIds); 

}
