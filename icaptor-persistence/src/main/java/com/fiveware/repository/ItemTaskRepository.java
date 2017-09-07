package com.fiveware.repository;

import com.fiveware.model.ItemTask;
import com.fiveware.model.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemTaskRepository extends CrudRepository<ItemTask, Long>{

	@Query(value = "FROM ItemTask WHERE statusProcess.name = :statusProcess")
	List<ItemTask> findItemTaskbyStatusProcess(@Param("statusProcess") String statusProcess);

	@Query(value = "FROM ItemTask i WHERE i.statusProcess.name IN (:statusProcess) AND i.task.id = :taskId")
	List<ItemTask> findItemTaskbyListStatusProcess(@Param("taskId") Long taskId, @Param("statusProcess") List<String> statusProcess);
	
	@Query("SELECT COUNT(i) FROM ItemTask i WHERE i.task.id = :taskId")
	Long countItemTask(@Param("taskId") Long taskId);

	List<ItemTask> findByTask(Task task);
}
