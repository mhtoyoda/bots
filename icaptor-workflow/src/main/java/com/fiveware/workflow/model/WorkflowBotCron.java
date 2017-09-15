package com.fiveware.workflow.model;

import java.io.Serializable;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Workflow_Bot_Cron")
public class WorkflowBotCron implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "workflow_id")
	private Workflow workflow;

	@Column(name = "day_execution")
	private Integer dayExecution;

	@Column(name = "time_execution")
	private LocalTime timeExecution;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Workflow getWorkflow() {
		return workflow;
	}

	public void setWorkflow(Workflow workflow) {
		this.workflow = workflow;
	}

	public Integer getDayExecution() {
		return dayExecution;
	}

	public void setDayExecution(Integer dayExecution) {
		this.dayExecution = dayExecution;
	}

	public LocalTime getTimeExecution() {
		return timeExecution;
	}

	public void setTimeExecution(LocalTime timeExecution) {
		this.timeExecution = timeExecution;
	}

}