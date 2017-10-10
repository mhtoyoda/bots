package com.fiveware.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bot")
public class Bot implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name ="name_bot")
	private String nameBot;
	
	@Column(name ="method")
	private String method;
	
	@Column(name ="endpoint")
	private String endpoint;
	
	@Column(name ="fields_input")
	private String fieldsInput;
	
	@Column(name ="fields_output")
	private String fieldsOutput;
	
	@Column(name ="type_file_in")
	private String typeFileIn;
	
	@Column(name ="separator_file")
	private String separatorFile;

	@Column(name ="version")
	private String vesion;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNameBot() {
		return nameBot;
	}

	public void setNameBot(String nameBot) {
		this.nameBot = nameBot;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getFieldsInput() {
		return fieldsInput;
	}

	public void setFieldsInput(String fieldsInput) {
		this.fieldsInput = fieldsInput;
	}

	public String getFieldsOutput() {
		return fieldsOutput;
	}

	public void setFieldsOutput(String fieldsOutput) {
		this.fieldsOutput = fieldsOutput;
	}

	public String getTypeFileIn() {
		return typeFileIn;
	}

	public void setTypeFileIn(String typeFileIn) {
		this.typeFileIn = typeFileIn;
	}

	public String getSeparatorFile() {
		return separatorFile;
	}

	public void setSeparatorFile(String separatorFile) {
		this.separatorFile = separatorFile;
	}

	public String getVesion() {
		return vesion;
	}

	public void setVesion(String vesion) {
		this.vesion = vesion;
	}
	
}