package com.fiveware.model;

import java.io.Serializable;

public class InputDictionaryContext implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7326799499068559448L;

	private String typeFileIn;
	private String[] fields;
	private String separator;

	public InputDictionaryContext(String typeFileIn, String[] fields, String separator) {
		this.typeFileIn = typeFileIn;
		this.fields = fields;
		this.separator = separator;
	}

	public String getTypeFileIn() {
		return typeFileIn;
	}

	public String[] getFields() {
		return fields;
	}

	public String getSeparator() {
		return separator;
	}

}
