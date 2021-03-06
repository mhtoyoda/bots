package com.fiveware.model;

public enum StatusProcessTaskEnum {

	CREATED(1L, "Created"),
	VALIDATING(2l, "Validating"),
	REJECTED(3L, "Rejected"),
	SCHEDULED(4L, "Scheduled"),	
	PROCESSING(5L, "Processing"),
	PROCESSED(6L, "Processed"),
	SUSPENDING(7L, "Suspending"),
	SUSPENDED(8L, "Suspended"),
	SUCCESS(9L, "Success"),
	ERROR(10l, "Error"),
	CANCELED(11L, "Canceled"),
	CONSOLIDATED(12L, "Consolidated");

	private StatusProcessTaskEnum(Long id, String name) {
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

	public StatusProcessTask getStatuProcess() {
		return new StatusProcessTask(this.id,this.name);
	}
}