package com.fiveware.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fiveware.model.entities.Agent;
import com.fiveware.model.entities.Bot;

@Repository
public interface AgentRepository extends CrudRepository<Agent, Long>{

	Optional<Agent> findByNameAgent(String agent);

	@Query("SELECT b FROM Agent a join a.bots b where a.nameAgent = :nameAgent")
	List<Bot> findBotsByAgent(@Param("nameAgent") String nameAgent);
}
