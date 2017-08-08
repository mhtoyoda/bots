package com.fiveware.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fiveware.model.Parameter;

@Repository
public interface ParameterRepository extends CrudRepository<Parameter, Long>{

}
