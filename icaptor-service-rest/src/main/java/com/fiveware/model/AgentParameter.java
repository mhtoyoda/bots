package com.fiveware.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "agent_parameter")
public class AgentParameter implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_agent")
	private Agent agent;

	@ManyToOne
	@JoinColumn(name = "id_parameter")
	private Parameter parameter;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@Column(name = "use_date")
	private LocalDateTime useDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public Parameter getParameter() {
		return parameter;
	}

	public void setParameter(Parameter parameter) {
		this.parameter = parameter;
	}

	public LocalDateTime getUseDate() {
		return useDate;
	}

	public void setUseDate(LocalDateTime useDate) {
		this.useDate = useDate;
	}
}