package com.fiveware.workflow.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fiveware.workflow.model.Workflow;

@Repository
public interface WorkFlowRepository extends CrudRepository<Workflow, Long>{

}
