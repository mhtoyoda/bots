package com.fiveware.workflow.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Workflow_Bot")
public class WorkflowBot implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "workflow_bot_step_id")
	private WorkflowBotStep workflowBotStep;

	@Column(name = "task_id")
	private Long taskId;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private StatusWorkflow status;

	@Column(name = "count_try")
	private Integer countTry;

	@Column(name = "date_created")
	private LocalDateTime dateCreated;

	@Column(name = "date_updated")
	private LocalDateTime dateUpdated;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public WorkflowBotStep getWorkflowBotStep() {
		return workflowBotStep;
	}

	public void setWorkflowBotStep(WorkflowBotStep workflowBotStep) {
		this.workflowBotStep = workflowBotStep;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public StatusWorkflow getStatus() {
		return status;
	}

	public void setStatus(StatusWorkflow status) {
		this.status = status;
	}

	public Integer getCountTry() {
		return countTry;
	}

	public void setCountTry(Integer countTry) {
		this.countTry = countTry;
	}

	public LocalDateTime getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(LocalDateTime dateCreated) {
		this.dateCreated = dateCreated;
	}

	public LocalDateTime getDateUpdated() {
		return dateUpdated;
	}

	public void setDateUpdated(LocalDateTime dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

}