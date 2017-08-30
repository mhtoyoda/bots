package com.fiveware.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fiveware.model.ItemTask;

@Repository
public interface ItemTaskRepository extends CrudRepository<ItemTask, Long>{

	@Query("FROM ItemTask WHERE statusProcess.name = :statusProcess")
	List<ItemTask> findItemTaskbyStatusProcess(@Param("statusProcess") String statusProcess);
	
	@Query("FROM ItemTask i WHERE i.statusProcess.name IN (:statusProcess) AND i.task.id = :taskId")
	List<ItemTask> findItemTaskbyListStatusProcess(@Param("taskId") Long taskId, @Param("statusProcess") List<String> statusProcess);
	
	@Query("SELECT COUNT(i) FROM ItemTask i WHERE i.task.id = :taskId")
	Long countItemTask(@Param("taskId") Long taskId);
}
