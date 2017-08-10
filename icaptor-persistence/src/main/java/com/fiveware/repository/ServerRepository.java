package com.fiveware.repository;

import com.fiveware.model.Agent;
import com.fiveware.model.Server;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServerRepository extends CrudRepository<Server, Long>{

	Optional<Server> findByName(String name);
	

	@Query("SELECT a FROM Server s JOIN s.agents a JOIN a.bots b WHERE s.name = :name AND b.nameBot = :nameBot AND b.endpoint = :endpoint")
	Optional<List<Agent>> getAllAgentsByBotName(@Param("name") String name, @Param("nameBot") String nameBot,
                                                @Param("endpoint") String endpoint);

	@Transactional(readOnly = false)
	Server save(Server server);

}
