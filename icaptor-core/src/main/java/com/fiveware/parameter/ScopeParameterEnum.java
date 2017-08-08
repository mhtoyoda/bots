package com.fiveware.parameter;

public enum ScopeParameterEnum {

	CLOUD(1L, "cloud"),
	BOT(2L, "bot"),
	CLOUD_BOT(3L, "cloud_bot"),
	SESSION(4L, "session");
	
	private ScopeParameterEnum(Long id, String name) {
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
