package com.fiveware.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fiveware.model.Task;
import com.fiveware.repository.task.TaskRepositoryQuery;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long>, TaskRepositoryQuery {

	@Query("FROM Task WHERE statusProcess.name = :statusProcess")
	List<Task> findTaskbyStatusProcess(@Param("statusProcess") String statusProcess);

	@Query("FROM Task WHERE bot.nameBot = :nameBot")
	List<Task> findTaskbyBot(@Param("nameBot") String nameBot);

	@Query("FROM Task WHERE usuario.id = :userId ORDER BY loadTime DESC")
	List<Task> findPeloUsuario(@Param("userId") Long userId);
	
	@Query("FROM Task WHERE statusProcess.name = :statusProcess AND startAt BETWEEN :start AND :end")
	List<Task> findTaskByStatusProcessAndStartAt(@Param("statusProcess") String statusProcess,
												 @Param("start") LocalDateTime start,
												 @Param("end") LocalDateTime end);
}
