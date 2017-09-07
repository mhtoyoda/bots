package com.fiveware.parameter;

import java.io.Serializable;

public class ParameterValue implements Serializable {

	private String field;
	private String value;

	public ParameterValue() {
	}

	public ParameterValue(String field, String value) {
		super();
		this.field = field;
		this.value = value;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
