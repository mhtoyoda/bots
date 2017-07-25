package com.fiveware.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fiveware.model.Agent;
import com.fiveware.model.Bot;

@Repository
public interface AgentRepository extends CrudRepository<Agent, Long> {

	Agent findByNameAgent(String nameAgent);

	@Query("SELECT b FROM Agent a join a.bots b where a.nameAgent = :nameAgent")
	List<Bot> findBynameAgent(@Param("nameAgent") String nameAgent);

}
