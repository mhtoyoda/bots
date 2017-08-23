package com.fiveware.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fiveware.model.Parameter;

@Repository
public interface ParameterRepository extends CrudRepository<Parameter, Long>{

	@Query("FROM Parameter p WHERE p.bot.nameBot = :botName")
	List<Parameter> findByBotName(@Param("botName") String botName);

}
