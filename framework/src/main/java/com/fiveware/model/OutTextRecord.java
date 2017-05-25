package com.fiveware.model;

import java.util.Map;

public class OutTextRecord extends Record {

	private Map<String, String> map;

	public OutTextRecord(Map<String, String> map) {
		this.map = map;
	}
	
	public Map<String, String> getMap() {
		return map;
	}
}
