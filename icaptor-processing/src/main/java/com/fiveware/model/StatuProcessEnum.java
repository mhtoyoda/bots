package com.fiveware.model;

public enum StatuProcessEnum {

	CREATED("Created"),
	VALIDATE("Validate"),
	PROCESSING("Processing"),
	PROCCESSED("Proccessed"),
	SUSPENDED("Suspended"),
	AVAILABLE("Available"),
	INLINE("InLine"),
	SUCCESS("Success"),
	ERROR("Error");

	private StatuProcessEnum(String name) {
		this.name = name;
	}

	private String name;
	
	public String getName() {
		return name;
	}	
}