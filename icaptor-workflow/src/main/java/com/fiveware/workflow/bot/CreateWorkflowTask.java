package com.fiveware.workflow.bot;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fiveware.model.Parameter;
import com.fiveware.service.ServiceParameter;
import com.fiveware.workflow.exception.TaskCreateException;
import com.fiveware.workflow.model.StatusWorkflow;
import com.fiveware.workflow.model.Workflow;
import com.fiveware.workflow.model.WorkflowBot;
import com.fiveware.workflow.model.WorkflowBotStep;
import com.fiveware.workflow.repository.WorkFlowBotRepository;
import com.fiveware.workflow.repository.WorkFlowBotStepRepository;
import com.fiveware.workflow.repository.WorkFlowRepository;
import com.google.common.collect.Lists;

@Component
public class CreateWorkflowTask {

	@Autowired
	private WorkFlowRepository workFlowRepository;
	
	@Autowired
	private ServiceParameter serviceParameter;
	
	@Autowired
	private WorkFlowBotStepRepository workFlowBotStepRepository;
	
	@Autowired
	private WorkFlowBotRepository workFlowBotRepository;
	
	@Autowired
	private ProcessWorkflowTask processWorkflowTask;
	
	public void createWorkflows() {
		Optional<Iterable<Workflow>> list = Optional.ofNullable(workFlowRepository.findAll());
		if(list.isPresent()){
			LocalDate localDate = LocalDate.now();
			List<Workflow> workflows = Lists.newArrayList(list.get()).stream().filter(workflow -> workflow.getActive()).collect(Collectors.toList());
			workflows.forEach(workflow -> {										
				Optional<Parameter> parameterTimeExecution = getParameterTimeExecution(workflow.getName(), day(localDate));
				if(parameterTimeExecution.isPresent()){
					List<WorkflowBotStep> workflowBotSteps = workflowBotStepList();
					Collections.sort(workflowBotSteps, Comparator.comparing(WorkflowBotStep::getSequence));
					workflowBotSteps.forEach(step -> {
						try {
							createWorkflow(step);
						} catch (TaskCreateException e) {
							throw new RuntimeException("Error create task workflow");
						}
					});
				}
			});
		}
	}
	
	private Optional<Parameter> getParameterTimeExecution(String workflowName, Integer dayExecution){
		List<Parameter> parameters = serviceParameter.findParameterByScopeAndType("cloud", "time_execution");
		String fieldValue = String.format("%s:%d", workflowName, dayExecution);
		Optional<Parameter> parameter = parameters.stream().filter(p -> p.getFieldValue().equals(fieldValue )).findFirst();
		return parameter;
	}
	
	private void createWorkflow(WorkflowBotStep workflowBotStep) throws TaskCreateException{
		WorkflowBot workflowBot = new WorkflowBot();
		workflowBot.setCountTry(0);
		workflowBot.setDateCreated(LocalDate.now());
		if(workflowBotStep.getSequence() == 1){
			Long taskId = processWorkflowTask.createTask(workflowBotStep);
			workflowBot.setTaskId(taskId);
			workflowBot.setStatus(StatusWorkflow.WAITING);		
		}else{
			workflowBot.setStatus(StatusWorkflow.NEW);			
		}
		workflowBot.setWorkflowBotStep(workflowBotStep);
		workFlowBotRepository.save(workflowBot);		
	}
	
	private List<WorkflowBotStep> workflowBotStepList(){
		return Lists.newArrayList(workFlowBotStepRepository.findAll());
	}
	
	private int day(LocalDate localDate){
		return localDate.getDayOfMonth();
	}
}
