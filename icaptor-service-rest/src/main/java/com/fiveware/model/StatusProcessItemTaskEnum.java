package com.fiveware.model;

public enum StatusProcessItemTaskEnum {
	
	AVAILABLE(1L, "Available"),
	INLINE(2L, "InLine"),
	PROCESSING(3L, "Processing"),
	SUCCESS(4L, "Success"),
	ERROR(5l, "Error"),
	CANCELED(6l, "Canceled");

	private StatusProcessItemTaskEnum(Long id, String name) {
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

	public StatusProcessItemTask getStatuProcess() {
		return new StatusProcessItemTask(this.id,this.name);
	}
}