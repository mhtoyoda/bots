package com.fiveware.model;

import java.io.Serializable;

public class BotReturnTypeFormatter implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8879228221602108868L;
	private String fieldIndex;
	private String typeFile;
	private String nameField;
	
	public BotReturnTypeFormatter(String fieldIndex, String typeFile, String nameField) {
		this.fieldIndex = fieldIndex;
		this.typeFile = typeFile;
		this.nameField = nameField;
	}

	public String getFieldIndex() {
		return fieldIndex;
	}

	public String getTypeFile() {
		return typeFile;
	}

	public String getNameField() {
		return nameField;
	}
}