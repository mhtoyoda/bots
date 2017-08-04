package com.fiveware.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fiveware.model.ItemTask;

@Repository
public interface ItemTaskRepository extends CrudRepository<ItemTask, Long>{

}
