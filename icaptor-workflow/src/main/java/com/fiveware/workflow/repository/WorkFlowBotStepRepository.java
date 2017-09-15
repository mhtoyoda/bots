package com.fiveware.workflow.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fiveware.workflow.model.WorkflowBotStep;

@Repository
public interface WorkFlowBotStepRepository extends CrudRepository<WorkflowBotStep, Long>{

}
