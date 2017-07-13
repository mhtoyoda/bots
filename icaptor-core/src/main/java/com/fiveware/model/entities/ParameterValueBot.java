package com.fiveware.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@AutoProperty
@Entity
@Table(name = "parameter_value_bot")
public class ParameterValueBot {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "parameter")
	private String parameter;

	@Column(name = "value")
	private String value;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "id_parameter_bot")
	private ParameterBot parameterBot;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public ParameterBot getParameterBot() {
		return parameterBot;
	}

	public void setParameterBot(ParameterBot parameterBot) {
		this.parameterBot = parameterBot;
	}


	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}
}