package com.fiveware.parameter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fiveware.model.Parameter;

public class ParameterInfo {

	private Map<String, Parameter> parameters;
	private Map<String, List<Parameter>> credentials;

	public ParameterInfo(){
		parameters = new HashMap<>();
		credentials = new HashMap<>();
	}
	
	public Map<String, Parameter> getParameters() {
		return parameters;
	}

	public void addParameters(String key, Parameter parameter) {
		parameters.put(key, parameter);
	}

	public Map<String, List<Parameter>> getCredentials() {
		return credentials;
	}

	public void addCredentials(String key, List<Parameter> parameters) {
		credentials.put(key, parameters);
	}
	
	public boolean hasParameters(){
		return parameters.isEmpty();
	}
	
	public boolean hasCredentials(){
		return credentials.isEmpty();
	}

}
