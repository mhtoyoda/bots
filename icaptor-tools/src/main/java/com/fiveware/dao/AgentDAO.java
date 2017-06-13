package com.fiveware.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fiveware.model.Agent;

@Repository
public interface AgentDAO extends CrudRepository<Agent, Long>{

}
