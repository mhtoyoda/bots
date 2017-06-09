package com.fiveware.builder;

import com.fiveware.model.InputDictionaryContext;

public class InputDictionaryContextBuilder {

	private String typeFileIn;
	private String[] fields;
	private String separator;
	
	public InputDictionaryContextBuilder(FieldsDictionary fieldsDictionary){
		this.typeFileIn = "txt";
		this.separator = "|";
		this.fields = fieldsDictionary.getFields();
	}
	
	public InputDictionaryContextBuilder typeFileIn(String typeFileIn){
		this.typeFileIn = typeFileIn;
		return this;
	}
	
	public InputDictionaryContextBuilder separator(String separator){
		this.separator = separator;
		return this;
	}
	
	public InputDictionaryContext build(){
		return new InputDictionaryContext(typeFileIn, fields, separator);
	}
}