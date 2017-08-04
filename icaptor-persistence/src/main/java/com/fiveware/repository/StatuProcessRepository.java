package com.fiveware.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fiveware.model.StatuProcess;

@Repository
public interface StatuProcessRepository extends CrudRepository<StatuProcess, Long>{

}
