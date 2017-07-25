package com.fiveware.repository;

import com.fiveware.model.ParameterBot;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParameterBotRepository extends CrudRepository<ParameterBot, Long>{

	@Query("select p FROM ParameterBot p WHERE p.bot.nameBot = :nameBot")
	List<ParameterBot> findByNameBotAndAtivoIsTrue(@Param("nameBot") String nameBot);
}
