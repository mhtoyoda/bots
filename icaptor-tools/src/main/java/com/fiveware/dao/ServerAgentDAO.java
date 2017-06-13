package com.fiveware.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fiveware.model.ServerAgent;

@Repository
public interface ServerAgentDAO extends CrudRepository<ServerAgent, Long>{

}
