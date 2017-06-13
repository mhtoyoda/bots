package com.fiveware.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "agent")
public class Agent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name ="nameAgent")
	private String nameAgent;
	
	@ManyToOne
	@JoinColumn(name ="serverId")
	private Server server;
	
	@Column(name ="ipAgent")
	private String ip;
	
	@ManyToMany
    @JoinTable(name="agent_bot", joinColumns=
    {@JoinColumn(name="idAgent")}, inverseJoinColumns=
      {@JoinColumn(name="idBot")})
	private List<Bot> bots;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNameAgent() {
		return nameAgent;
	}

	public void setNameAgent(String nameAgent) {
		this.nameAgent = nameAgent;
	}

	public List<Bot> getBots() {
		return bots;
	}

	public void setBots(List<Bot> bots) {
		this.bots = bots;
	}

}
