package com.fiveware.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "parameter_bot")
public class ParameterBot implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1667658363527365607L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;

	@ManyToOne
	@JoinColumn(name = "id_bot")
	private Bot bot;

	private Boolean ativo;

	@OneToMany(mappedBy = "parameterBot")
	private List<ParameterValueBot> parameterValues;
	
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

	public Bot getBot() {
		return bot;
	}

	public void setBot(Bot bot) {
		this.bot = bot;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public List<ParameterValueBot> getParameterValues() {
		return parameterValues;
	}

	public void setParameterValues(List<ParameterValueBot> parameterValues) {
		this.parameterValues = parameterValues;
	}
}