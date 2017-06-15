package com.fiveware.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fiveware.model.Agent;
import com.fiveware.model.Server;

@Repository
public interface ServerDAO extends CrudRepository<Server, Long>{

	Optional<Server> findByName(String name);
	
	@Query("SELECT a FROM Server s JOIN s.agents a WHERE s.name = :name")
	List<Agent> getAllAgents(@Param("name") String name);
}
