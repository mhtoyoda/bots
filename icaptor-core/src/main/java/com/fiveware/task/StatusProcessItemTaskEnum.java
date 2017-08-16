package com.fiveware.task;

import com.fiveware.model.StatuProcessItemTask;

public enum StatusProcessItemTaskEnum {
	
	AVAILABLE(1L, "Available"),
	INLINE(2L, "InLine"),
	PROCESSING(3L, "Processing"),
	SUCCESS(4L, "Success"),
	ERROR(5l, "Error");

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

	public StatuProcessItemTask getStatuProcess() {
		return new StatuProcessItemTask(this.id,this.name);
	}
}