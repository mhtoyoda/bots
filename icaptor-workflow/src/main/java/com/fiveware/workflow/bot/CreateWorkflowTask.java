package com.fiveware.workflow.bot;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fiveware.workflow.model.StatusWorkflow;
import com.fiveware.workflow.model.Workflow;
import com.fiveware.workflow.model.WorkflowBot;
import com.fiveware.workflow.model.WorkflowBotCron;
import com.fiveware.workflow.model.WorkflowBotStep;
import com.fiveware.workflow.repository.WorkFlowBotCronRepository;
import com.fiveware.workflow.repository.WorkFlowBotRepository;
import com.fiveware.workflow.repository.WorkFlowBotStepRepository;
import com.fiveware.workflow.repository.WorkFlowRepository;
import com.google.common.collect.Lists;

@Component
public class CreateWorkflowTask {

	@Autowired
	private WorkFlowRepository workFlowRepository;
	
	@Autowired
	private WorkFlowBotCronRepository workFlowBotCronRepository;
	
	@Autowired
	private WorkFlowBotStepRepository workFlowBotStepRepository;
	
	@Autowired
	private WorkFlowBotRepository workFlowBotRepository;
	
	@Autowired
	private ProcessWorkflowTask processWorkflowTask;
	
	public void createWorkflowsCron() {
		Optional<Iterable<Workflow>> list = Optional.ofNullable(workFlowRepository.findAll());
		if(list.isPresent()){
			LocalDateTime localDateTime = LocalDateTime.now();
			List<Workflow> workflows = Lists.newArrayList(list.get()).stream().filter(workflow -> workflow.getActive()).collect(Collectors.toList());
			workflows.forEach(workflow -> {
				Integer dayExecution = day(localDateTime);
				LocalTime timeExecution = hour(localDateTime);
				Optional<WorkflowBotCron> optional = workFlowBotCronRepository.findWorkflowIntTime(workflow.getId(), dayExecution, timeExecution);
				if(optional.isPresent()){
					List<WorkflowBotStep> workflowBotSteps = workflowBotStepList();
					Collections.sort(workflowBotSteps, Comparator.comparing(WorkflowBotStep::getSequence));
					workflowBotSteps.forEach(step -> {
						createWorkflowStep(step);
					});
				}
			});
		}
	}
	
	private void createWorkflowStep(WorkflowBotStep workflowBotStep){
		WorkflowBot workflowBot = new WorkflowBot();
		workflowBot.setCountTry(0);
		workflowBot.setDateCreated(LocalDateTime.now());
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
	
	private int day(LocalDateTime localDateTime){
		return localDateTime.getDayOfMonth();
	}
	
	private LocalTime hour(LocalDateTime localDateTime){
		return LocalTime.of(localDateTime.getHour(), localDateTime.getMinute());		
	}

}
