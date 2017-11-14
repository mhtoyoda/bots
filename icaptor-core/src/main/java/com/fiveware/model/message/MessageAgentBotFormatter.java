package com.fiveware.model.message;

import java.io.Serializable;

public class MessageAgentBotFormatter implements Serializable {

	private String fieldIndex;
	private String typeFile;
	private String nameField;

	public String getFieldIndex() {
		return fieldIndex;
	}

	public void setFieldIndex(String fieldIndex) {
		this.fieldIndex = fieldIndex;
	}

	public String getTypeFile() {
		return typeFile;
	}

	public void setTypeFile(String typeFile) {
		this.typeFile = typeFile;
	}

	public String getNameField() {
		return nameField;
	}

	public void setNameField(String nameField) {
		this.nameField = nameField;
	}

}
