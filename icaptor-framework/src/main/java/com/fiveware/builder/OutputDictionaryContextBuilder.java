package com.fiveware.builder;

import com.fiveware.model.OutputDictionaryContext;

public class OutputDictionaryContextBuilder {

	private String typeFileOut;
	private String[] fields;
	private String separator;
	private String nameFileOut;
	
	public OutputDictionaryContextBuilder(FieldsDictionary fieldsDictionary){
		this.typeFileOut = "txt";
		this.separator = "|";
		this.nameFileOut = "saida";
		this.fields = fieldsDictionary.getFields();
	}
	
	public OutputDictionaryContextBuilder typeFileOut(String typeFileOut){
		this.typeFileOut = typeFileOut;
		return this;
	}
	
	public OutputDictionaryContextBuilder separator(String separator){
		this.separator = separator;
		return this;
	}
	
	public OutputDictionaryContextBuilder nameFileOut(String nameFileOut){
		this.nameFileOut = nameFileOut;
		return this;
	}
	
	public OutputDictionaryContext build(){
		return new OutputDictionaryContext(typeFileOut, fields, separator, nameFileOut);
	}
}
