package com.fiveware.parameter;

import java.io.Serializable;

public class ParameterValue implements Serializable {

	private String field;
	private String value;
	
	public ParameterValue(String field, String value) {
		super();
		this.field = field;
		this.value = value;
	}
	public String getField() {
		return field;
	}
	public String getValue() {
		return value;
	}
}
