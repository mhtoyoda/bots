package com.fiveware.model;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.Cascade;
import org.pojomatic.Pojomatic;

import com.fiveware.model.user.IcaptorUser;
import org.pojomatic.annotations.AutoProperty;

@AutoProperty
@Entity
@Table(name = "parameter")
public class Parameter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "field_value")
	private String fieldValue;

	@Column(name = "active")
	private Boolean active;

	@ManyToOne
	@JoinColumn(name = "id_bot")
	private Bot bot;

	@ManyToOne
	@JoinColumn(name = "id_user")
	private IcaptorUser usuario;

	@ManyToOne
	@JoinColumn(name = "id_cloud")
	private Server cloud;

	@ManyToOne
	@JoinColumn(name = "id_scope_parameter")
	private ScopeParameter scopeParameter;

	@ManyToOne
	@JoinColumn(name = "id_type_parameter")
	private TypeParameter typeParameter;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Bot getBot() {
		return bot;
	}

	public void setBot(Bot bot) {
		this.bot = bot;
	}

	public IcaptorUser getUsuario() {
		return usuario;
	}

	public void setUsuario(IcaptorUser usuario) {
		this.usuario = usuario;
	}

	public Server getCloud() {
		return cloud;
	}

	public void setCloud(Server cloud) {
		this.cloud = cloud;
	}

	public ScopeParameter getScopeParameter() {
		return scopeParameter;
	}

	public void setScopeParameter(ScopeParameter scopeParameter) {
		this.scopeParameter = scopeParameter;
	}

	public TypeParameter getTypeParameter() {
		return typeParameter;
	}

	public void setTypeParameter(TypeParameter typeParameter) {
		this.typeParameter = typeParameter;
	}

	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}

}