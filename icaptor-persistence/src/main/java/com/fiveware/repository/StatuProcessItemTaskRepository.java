package com.fiveware.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fiveware.model.StatuProcessItemTask;

@Repository
public interface StatuProcessItemTaskRepository extends CrudRepository<StatuProcessItemTask, Long>{

}
