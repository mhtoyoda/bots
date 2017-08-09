package com.fiveware.task;

public enum StatuProcessEnum {

	CREATED(1L, "Created"),
	VALIDATING(2l, "Validating"),
	REJECTED(3L, "Rejected"),
	SCHEDULED(4L, "Scheduled"),	
	PROCESSING(5L, "Processing"),
	PROCCESSED(6L, "Proccessed"),
	SUSPENDED(7L, "Suspended"),
	AVAILABLE(8L, "Available"),
	INLINE(9L, "InLine"),
	SUCCESS(10L, "Success"),
	ERROR(11l, "Error"),
	CANCELED(12L, "Canceled");

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