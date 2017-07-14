package com.fiveware.model;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import javax.persistence.*;
import java.util.List;

@AutoProperty
@Entity
@Table(name = "agent")
public class Agent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name_agent")
	private String nameAgent;

	@Column(name = "ip_agent")
	private String ip;
	
	@Column(name = "port_agent")
	private int port;

	@ManyToOne
	@JoinColumn(name = "server_id")
	private Server server;

	@ManyToMany
	@JoinTable(name = "agent_bot", joinColumns = { @JoinColumn(name = "id_agent") },
			   inverseJoinColumns = {@JoinColumn(name = "id_bot") })
	private List<Bot> bots;

	public Agent() {
	}

	public Agent(Long id, String nameAgent, int port, String ip, Server server, List<Bot> bots) {
		this.id=id;
		this.nameAgent=nameAgent;
		this.port=port;
		this.ip=ip;
		this.server=server;
		this.bots=bots;
	}

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

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}




	public static class BuilderAgent{
		private Long id;
		private String nameAgent;
		private String ip;
		private int port;
		private Server server;
		private List<Bot> bots;

		public BuilderAgent() {
		}

		public BuilderAgent id(Long id){
			this.id=id;
			return this;
		}
		public BuilderAgent nameAgent(String name){
			this.nameAgent=name;
			return this;
		}
		public BuilderAgent ip(String ip){
			this.ip=ip;
			return this;
		}

		public BuilderAgent port(Integer port){
			this.port=port;
			return this;
		}

		public BuilderAgent server(Server server){
			this.server=server;
			return this;
		}

		public BuilderAgent bots(List<Bot> bots){
			this.bots=bots;
			return this;
		}

		public Agent build(){
			return new Agent(this.id,this.nameAgent,this.port,this.ip,this.server,this.bots);
		}
	}


}