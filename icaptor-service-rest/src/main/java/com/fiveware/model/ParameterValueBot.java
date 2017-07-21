package com.fiveware.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "parameter_value_bot")
public class ParameterValueBot implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "parameter")
	private String parameter;

	@Column(name = "value")
	private String value;

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

}