package com.fiveware.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

@AutoProperty
@Entity
@Table(name = "type_parameter")
public class TypeParameter implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "exclusive")
	private Boolean exclusive;
	
	@Column(name = "credential")
	private Boolean credential;
	
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

	public Boolean getExclusive() {
		return exclusive;
	}

	public void setExclusive(Boolean exclusive) {
		this.exclusive = exclusive;
	}
	
	public Boolean getCredential() {
		return credential;
	}

	public void setCredential(Boolean credential) {
		this.credential = credential;
	}

	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}

	@JsonIgnore
	public boolean isNew() {
		return Objects.isNull(this.id);
	}

	@JsonIgnore
	public boolean isTypeLoginAndCredential(){
		return ("login".equalsIgnoreCase(this.getName() ) || "credential".equalsIgnoreCase(this.getName() ));
	}
}
