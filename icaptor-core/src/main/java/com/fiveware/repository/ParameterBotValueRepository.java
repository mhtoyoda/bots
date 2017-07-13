package com.fiveware.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fiveware.model.entities.ParameterValueBot;

@Repository
public interface ParameterBotValueRepository extends CrudRepository<ParameterValueBot, Long>{

	@Query("FROM ParameterValueBot p WHERE p.parameterBot.bot.nameBot = :nameBot AND p.parameterBot.ativo = 1")
	List<ParameterValueBot> findByParameterBotValues(@Param("nameBot") String nameBot);
}
