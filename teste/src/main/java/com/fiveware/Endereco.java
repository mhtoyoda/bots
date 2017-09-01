package com.fiveware;

import java.io.Serializable;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import com.fiveware.annotation.Field;

@AutoProperty
public class Endereco implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4321879773490723975L;
	@Field(name = "logradouro")
	private String logradouro;
	@Field(name = "bairro")
	private String bairro;
	@Field(name = "localidade")
	private String localidade;
	@Field(name = "cep", length = 9, regexValidate = "\\d{5}\\-?\\d{3}")
	private String cep;

	public Endereco(){}
	public Endereco(String logradouro, String bairro, String localidade, String cep) {
		this.logradouro = logradouro;
		this.bairro = bairro;
		this.localidade = localidade;
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getLocalidade() {
		return localidade;
	}

	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}
}
