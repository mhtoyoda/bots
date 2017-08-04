package com.fiveware.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fiveware.model.Task;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long>{

}
