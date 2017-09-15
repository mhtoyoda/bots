package com.fiveware.workflow.repository;

import java.time.LocalTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fiveware.workflow.model.WorkflowBotCron;

@Repository
public interface WorkFlowBotCronRepository extends CrudRepository<WorkflowBotCron, Long>{
	
	@Query("FROM WorkflowBotCron WHERE workflow.id = :workflowId AND dayExecution = :dayExecution AND timeExecution = :timeExecution")
	public Optional<WorkflowBotCron> findWorkflowIntTime(@Param("workflowId") Long workflowId, @Param("dayExecution") Integer dayExecution,
			@Param("timeExecution") LocalTime timeExecution);
}
