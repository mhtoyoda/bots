package com.fiveware.model.entities;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import javax.persistence.*;
import java.util.List;

@AutoProperty
@Entity
@Table(name = "bot")
public class Bot {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name ="name_bot")
	private String nameBot;
	
	@Column(name ="method")
	private String method;
	
	@Column(name ="endpoint")
	private String endpoint;
	
	@OneToMany
	private List<ParameterBot> parameterBots;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNameBot() {
		return nameBot;
	}

	public void setNameBot(String nameBot) {
		this.nameBot = nameBot;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public List<ParameterBot> getParameterBots() {
		return parameterBots;
	}

	public void setParameterBots(List<ParameterBot> parameterBots) {
		this.parameterBots = parameterBots;
	}


	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}
}
