package com.fiveware.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fiveware.model.StatusProcessItemTask;

@Repository
public interface StatuProcessItemTaskRepository extends CrudRepository<StatusProcessItemTask, Long>{

}
