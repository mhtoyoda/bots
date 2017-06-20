package com.fiveware.validate;

import java.io.Serializable;

import com.fiveware.annotation.Field;

public class Endereco implements Serializable {
	
	@Field(name = "rua", length = 100)
	private String logradouro;
	
	@Field(name = "num", length = 3)
	private Integer numero;

	@Field(name = "cep", length = 9, regexValidate = "\\d{5}\\-?\\d{3}")
	private String cep;

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	@Override
	public String toString() {
		return "Endereco [logradouro=" + logradouro + ", numero=" + numero + ", cep=" + cep + "]";
	}
}