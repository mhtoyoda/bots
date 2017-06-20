package com.fiveware.model;

import java.io.Serializable;

public class FieldBotContext implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7477895198056649568L;

	private String name;
	private String regexValidate;
	private Integer position;
	private Integer length;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public String getRegexValidate() {
		return regexValidate;
	}

	public void setRegexValidate(String regexValidate) {
		this.regexValidate = regexValidate;
	}
}