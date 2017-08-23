package com.fiveware.helpers;

import com.fiveware.model.IcaptorPameterContext;

public class ParameterContextBuilder {

	private String name;
	private String value;
	private String regexValidate;
	private String nameTypeParameter;
	private boolean exclusive;
	
	public ParameterContextBuilder name(String name){
		this.name = name;
		return this;
	}
	
	public ParameterContextBuilder value(String value){
		this.value = value;
		return this;
	}
	
	public ParameterContextBuilder regexValidate(String regexValidate){
		this.regexValidate = regexValidate;
		return this;
	}
	
	public ParameterContextBuilder nameTypeParameter(String nameTypeParameter){
		this.nameTypeParameter = nameTypeParameter;
		return this;
	}
	
	public ParameterContextBuilder exclusive(boolean exclusive){
		this.exclusive = exclusive;
		return this;
	}
	
	public IcaptorPameterContext build(){
		return new IcaptorPameterContext(name, value, regexValidate, nameTypeParameter, exclusive);
	}
}