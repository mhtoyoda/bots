package com.fiveware.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fiveware.model.TaskFile;

@Repository
public interface TaskFileRepository extends CrudRepository<TaskFile, Long>{

	@Query(value = "FROM TaskFile WHERE task.id = :taskId")
	List<TaskFile> findFilebyTaskId(@Param("taskId") Long taskId);
}
