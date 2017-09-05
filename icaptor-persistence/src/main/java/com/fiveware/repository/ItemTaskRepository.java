package com.fiveware.repository;

import java.util.List;

import com.fiveware.model.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fiveware.model.ItemTask;

@Repository
public interface ItemTaskRepository extends CrudRepository<ItemTask, Long>{

	@Query("FROM ItemTask WHERE statusProcess.name = :statusProcess")
	List<ItemTask> findItemTaskbyStatusProcess(@Param("statusProcess") String statusProcess);

	List<ItemTask> findByTask(Task task);
}
