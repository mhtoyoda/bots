package com.fiveware.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
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
public class Agent implements Serializable {

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
	@JoinColumn(name ="id_server")
	private Server server;

	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable(name = "agent_bot", joinColumns = { @JoinColumn(name = "id_agent") },
			   inverseJoinColumns = {@JoinColumn(name = "id_bot") })
	private List<Bot> bots;

	@Column(name = "address_host")
	private String addressHost;

	public Agent(){}

	public Agent(Long id, String nameAgent, int port, String ip, Server server, List<Bot> bots, String addressHost) {
		this.id=id;
		this.nameAgent=nameAgent;
		this.port=port;
		this.ip=ip;
		this.server=server;
		this.bots=bots;
		this.addressHost=addressHost;
	}


	public String getAddressHost() {
		return addressHost;
	}

	public void setAddressHost(String addressHost) {
		this.addressHost = addressHost;
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

	public static class BuilderAgent{
		private Long id;
		private String nameAgent;
		private String ip;
		private int port;
		private Server server;
		private List<Bot> bots;
		private String addressHost;

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
			this.server = server;
			return this;
		}

		public BuilderAgent bots(List<Bot> bots){
			this.bots=bots;
			return this;
		}

		public BuilderAgent addressHost(String addressHost){
			this.addressHost = addressHost;
			return this;
		}

		public Agent build(){
			return new Agent(this.id,this.nameAgent,this.port,this.ip,this.server,this.bots,this.addressHost);
		}
	}


}