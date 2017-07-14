package com.fiveware.repository;

import com.fiveware.model.Agent;
import com.fiveware.model.Bot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface AgentRepository extends JpaRepository<Agent, Long> {

	Optional<Agent> findByNameAgent(String agent);

	@Query("SELECT b FROM Agent a join a.bots b where a.nameAgent = :nameAgent")
	List<Bot> findBotsByAgent(@Param("nameAgent") String nameAgent);

}
