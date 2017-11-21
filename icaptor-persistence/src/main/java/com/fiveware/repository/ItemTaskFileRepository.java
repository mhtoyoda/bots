package com.fiveware.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fiveware.model.ItemTaskFile;

@Repository
public interface ItemTaskFileRepository extends CrudRepository<ItemTaskFile, Long>{

	@Query(value = "FROM ItemTaskFile WHERE itemTask.id = :itemTaskId")
	List<ItemTaskFile> findFilebyItemTaskId(@Param("itemTaskId") Long itemTaskId);
}
