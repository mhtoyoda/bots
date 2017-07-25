package com.fiveware.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fiveware.model.Task;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long>{

	@Query("FROM Task t WHERE t.bot.nameBot = :nameBot AND t.status = :status")
	List<Task> getAllTaskBotByStatus(@Param("nameBot") String nameBot, @Param("status") String status);

}
