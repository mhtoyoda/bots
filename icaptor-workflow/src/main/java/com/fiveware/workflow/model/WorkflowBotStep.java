package com.fiveware.workflow.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Workflow_Bot_Step")
public class WorkflowBotStep implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "bot_source")
	private String botSource;
	
	@Column(name = "bot_target")
	private String botTarget;
	
	private Integer sequence;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBotSource() {
		return botSource;
	}

	public void setBotSource(String botSource) {
		this.botSource = botSource;
	}

	public String getBotTarget() {
		return botTarget;
	}

	public void setBotTarget(String botTarget) {
		this.botTarget = botTarget;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	
}