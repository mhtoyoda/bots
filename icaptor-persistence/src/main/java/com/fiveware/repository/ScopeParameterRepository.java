package com.fiveware.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fiveware.model.ScopeParameter;

@Repository
public interface ScopeParameterRepository extends CrudRepository<ScopeParameter, Long>{

}
