package com.fiveware.model;

import java.io.Serializable;

public class OutputDictionaryContext implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5750303112614685985L;

	private String typeFileOut;
	private String[] fields;
	private String separator;
	private String nameFileOut;

	public OutputDictionaryContext(String typeFileOut, String[] fields, String separator, String nameFileOut) {
		this.typeFileOut = typeFileOut;
		this.fields = fields;
		this.separator = separator;
		this.nameFileOut = nameFileOut;
	}

	public String getTypeFileOut() {
		return typeFileOut;
	}

	public String[] getFields() {
		return fields;
	}

	public String getSeparator() {
		return separator;
	}

	public String getNameFileOut() {
		return nameFileOut;
	}

}
