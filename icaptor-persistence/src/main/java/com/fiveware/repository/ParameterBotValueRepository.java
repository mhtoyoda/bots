package com.fiveware.repository;

import com.fiveware.model.entities.ParameterValueBot;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParameterBotValueRepository extends CrudRepository<ParameterValueBot, Long>{

	@Query("select p from ParameterValueBot p WHERE p.parameterBot.bot.nameBot = :nameBot AND p.parameterBot.ativo = 1")
	List<ParameterValueBot> findByParameterBotValues(@Param("nameBot") String nameBot);
}
