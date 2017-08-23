package com.fiveware.model;

import java.io.Serializable;

public class IcaptorPameterContext implements Serializable {

	private String name;
	private String value;
	private String regex;
	private String nameTypeParameter;
	private boolean exclusive;

	public IcaptorPameterContext(String name, String value, String regex, String nameTypeParameter, boolean exclusive) {
		super();
		this.name = name;
		this.value = value;
		this.regex = regex;
		this.nameTypeParameter = nameTypeParameter;
		this.exclusive = exclusive;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	public String getRegex() {
		return regex;
	}

	public String getNameTypeParameter() {
		return nameTypeParameter;
	}

	public boolean isExclusive() {
		return exclusive;
	}

}