package com.fiveware.repository;

import com.fiveware.model.Agent;
import com.fiveware.model.Bot;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgentRepository extends CrudRepository<Agent, Long> {

	Agent findByNameAgent(String nameAgent);

	@Query("SELECT b FROM Agent a join a.bots b where a.nameAgent = :nameAgent")
	List<Bot> findBynameAgent(@Param("nameAgent") String nameAgent);

    Agent findByNameAgentAndPort(String nameAgent, int port);
    
    @Query("SELECT a FROM Agent a join a.bots b where b.nameBot = :nameBot")
	List<Agent> findByBot(@Param("nameBot") String nameBot);
}
