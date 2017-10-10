package com.fiveware.model.message;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

public class MessageAgentBot implements Serializable{
	
	private String nameBot;
	private String nameMethod;
	private String endpoint;
	private String fieldsInput;	
	private String fieldsOutput;
	private String typeFileIn;
	private String separatorFile;
	private String version;
	private String description;
	private String classloader;
	private List<MessageParameterAgentBot> parameters;
	
	public String getNameBot() {
		return nameBot;
	}

	public void setNameBot(String nameBot) {
		this.nameBot = nameBot;
	}

	public String getNameMethod() {
		return nameMethod;
	}

	public void setNameMethod(String nameMethod) {
		this.nameMethod = nameMethod;
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
	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getClassloader() {
		return classloader;
	}

	public void setClassloader(String classloader) {
		this.classloader = classloader;
	}

	public List<MessageParameterAgentBot> getParameters() {
		return parameters;
	}

	public void setParameters(List<MessageParameterAgentBot> parameters) {
		this.parameters = parameters;
	}
	
	public void addParameters(MessageParameterAgentBot parameter) {
		if( null == parameters){
			this.parameters = Lists.newArrayList();			
		}
		parameters.add(parameter);
	}
}
