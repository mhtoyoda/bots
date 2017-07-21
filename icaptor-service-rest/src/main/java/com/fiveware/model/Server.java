package com.fiveware.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "server")
public class Server implements Serializable,Comparable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonSerialize
	@JsonProperty("id")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "host")
	private String host;

	@JsonBackReference
	@ManyToMany(mappedBy = "server")
	private Set<Agent> agents;

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

	public Set<Agent> getAgents() {return agents;}

	public void setAgents(Set<Agent> agents) {this.agents = agents;}

	@Override
	public int compareTo(Object o) {
		if (this.id==null||((Server)o).id==null) return -1;
		return this.id.compareTo(((Server)o).id);
	}
}
