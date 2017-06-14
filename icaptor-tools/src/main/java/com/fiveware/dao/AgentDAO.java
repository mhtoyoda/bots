package com.fiveware.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fiveware.model.Agent;

@Repository
public interface AgentDAO extends CrudRepository<Agent, Long>{

	Optional<Agent> findByNameAgent(String agent);

}
