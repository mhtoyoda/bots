package com.fiveware.model.message;

import java.io.Serializable;

public class MessageParameterAgentBot implements Serializable{

	private String typeParameterName;
	private Boolean typeParameterExclusive;
	private Boolean typeParameterCredential;
	private String parameterValue;

	public String getTypeParameterName() {
		return typeParameterName;
	}

	public void setTypeParameterName(String typeParameterName) {
		this.typeParameterName = typeParameterName;
	}

	public Boolean getTypeParameterExclusive() {
		return typeParameterExclusive;
	}

	public void setTypeParameterExclusive(Boolean typeParameterExclusive) {
		this.typeParameterExclusive = typeParameterExclusive;
	}

	public Boolean getTypeParameterCredential() {
		return typeParameterCredential;
	}

	public void setTypeParameterCredential(Boolean typeParameterCredential) {
		this.typeParameterCredential = typeParameterCredential;
	}

	public String getParameterValue() {
		return parameterValue;
	}

	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}

}
