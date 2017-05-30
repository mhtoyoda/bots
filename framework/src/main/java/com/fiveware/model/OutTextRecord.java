package com.fiveware.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class OutTextRecord {

	@JsonProperty("bot")
	private Map<String, Object> map;
	
	public OutTextRecord(Map<String, Object> map) {
		this.map = map;
	}
	
	public Map<String, Object> getMap() {
		return map;
	}
}
