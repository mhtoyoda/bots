package com.fiveware.model;

import java.io.Serializable;

public class IcaptorPameterContext implements Serializable {

	private String value;
	private String regex;
	private String nameTypeParameter;
	private boolean exclusive;
	private boolean credential;

	public IcaptorPameterContext(String value, String regex, String nameTypeParameter, boolean exclusive, boolean credential) {
		super();
		this.value = value;
		this.regex = regex;
		this.nameTypeParameter = nameTypeParameter;
		this.exclusive = exclusive;
		this.credential = credential;
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

	public boolean isCredential() {
		return credential;
	}

}