package com.fiveware.model;

import java.util.HashMap;
import java.util.Map;

public class ParameterBot {

	private Map<String, String> parameters;
	
	public void addParameter(String key, String value){
		if(parameters == null){
			parameters = new HashMap<String, String>(); 
		}
		parameters.put(key, value);
	}

	public Map<String, String> getParameters() {
		return parameters;
	}
	
}
