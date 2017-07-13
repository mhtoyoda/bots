package com.fiveware.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fiveware.model.entities.Task;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long>{

}
