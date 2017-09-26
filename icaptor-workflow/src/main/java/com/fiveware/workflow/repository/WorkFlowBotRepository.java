package com.fiveware.workflow.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fiveware.workflow.model.StatusWorkflow;
import com.fiveware.workflow.model.WorkflowBot;

@Repository
public interface WorkFlowBotRepository extends CrudRepository<WorkflowBot, Long>{
	
	@Query("FROM WorkflowBot WHERE status = :status")
	WorkflowBot findByStatus(@Param("status") StatusWorkflow status);

	@Query("FROM WorkflowBot WHERE status = :status AND workflowBotStep.botSource = :bot")
	WorkflowBot findWorkflowBotStepByStatus(@Param("bot") String bot, @Param("status") StatusWorkflow status);
	
	@Query("FROM WorkflowBot WHERE dateCreated = :dateCreated")
	List<WorkflowBot> findByDateCreated(@Param("dateCreated") LocalDate dateCreated);
}