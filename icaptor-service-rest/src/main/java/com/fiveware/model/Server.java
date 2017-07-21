package com.fiveware.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@AutoProperty
@Entity
@Table(name = "server")
public class Server implements Serializable{


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonSerialize
	@JsonProperty("id")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "host")
	private String host;

	@OneToMany
	private List<Agent> agents;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public List<Agent> getAgents() {
		return agents;
	}

	public void setAgents(List<Agent> agents) {
		this.agents = agents;
	}

	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}
}
