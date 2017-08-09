package com.fiveware.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.StringJoiner;

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


	public String fieldsToLine() {
		StringJoiner joiner = new StringJoiner(this.separator);
		Arrays.asList(this.fields).forEach((v) -> joiner.add((CharSequence) v));
		return joiner.toString();
	}

}
