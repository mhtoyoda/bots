package com.fiveware.task;

public enum StatuProcessEnum {

	CREATED(1L, "Created"),
	VALIDATE(2l, "Validate"),
	PROCESSING(3L, "Processing"),
	PROCCESSED(4L, "Proccessed"),
	SUSPENDED(5L, "Suspended"),
	AVAILABLE(6L, "Available"),
	INLINE(7L, "InLine"),
	SUCCESS(8L, "Success"),
	ERROR(9l, "Error");

	private StatuProcessEnum(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	private Long id;
	
	private String name;
	
	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}	
}