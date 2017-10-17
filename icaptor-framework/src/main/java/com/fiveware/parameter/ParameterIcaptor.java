package com.fiveware.parameter;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ParameterIcaptor implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2590382722023093609L;
	private String type;
	private String field;
	private String value;

	public ParameterIcaptor(){}
	
	@JsonCreator
	public ParameterIcaptor(@JsonProperty("type") String type, @JsonProperty("field") String field, @JsonProperty("value") String value) {
		this.type = type;
		this.field = field;
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public String getField() {
		return field;
	}

	public String getValue() {
		return value;
	}

}
