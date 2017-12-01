package com.fiveware.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fiveware.model.StatusProcessTask;

@Repository
public interface StatuProcessRepository extends CrudRepository<StatusProcessTask, Long>{

}
