package com.fiveware.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fiveware.model.entities.ParameterBot;

@Repository
public interface ParameterBotRepository extends CrudRepository<ParameterBot, Long>{

	@Query("FROM ParameterBot p WHERE p.bot.nameBot = :nameBot")
	List<ParameterBot> findByNameBotAndAtivoIsTrue(@Param("nameBot") String nameBot);
}
