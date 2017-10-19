package com.fiveware.repository;

import com.fiveware.model.Parameter;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParameterRepository extends CrudRepository<Parameter, Long> {

	@Query("FROM Parameter p WHERE p.bot.nameBot = :botName")
	List<Parameter> findByBotName(@Param("botName") String botName);

	@Query("FROM Parameter p WHERE p.scopeParameter.name = :nameScope AND p.typeParameter.name = :nameType")
	List<Parameter> findParameterByScopeParameterNameAndTypeParameterName(@Param("nameScope") String nameScope, @Param("nameType") String nameType);

	@Query("FROM Parameter p WHERE p.bot.nameBot = :botName AND p.usuario.id = :userId")
	List<Parameter> findParametersByBotNameAndUser(@Param("botName") String botName, @Param("userId") Long userId);

	@Query("FROM Parameter p WHERE p.bot.nameBot = :botName AND p.scopeParameter.name = :nameScope")
	List<Parameter> findParametersByBotNameAndPriority(@Param("botName") String botName, @Param("nameScope") String nameScope);

    List<Parameter> findParameterByScopeParameterName(String name);

	Parameter findParameterByTypeParameterNameAndScopeParameterName(@Param("nameScope") String nameScope, @Param("nameType") String nameType);

    Iterable<Parameter> findByUsuarioId(Long id);
}
