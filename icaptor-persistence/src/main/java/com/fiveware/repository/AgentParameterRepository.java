package com.fiveware.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fiveware.model.AgentParameter;

@Repository
public interface AgentParameterRepository extends CrudRepository<AgentParameter, Long>{

	@Query("FROM AgentParameter WHERE parameter.id = :parameterId")
	AgentParameter findByParameterId(@Param("parameterId") Long parameterId);
	
}
