package com.fiveware.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class Record {

	private Map<String, Object> recordMap = new LinkedHashMap<String, Object>();

	public Record() {
	}

	public Record(Map<String, Object> recordMap) {
		this.recordMap = recordMap;
	}

	public synchronized void addRecordLine(String field, Object value) {
		recordMap.put(field, value);
	}

	public synchronized Map<String, Object> getRecordMap() {
		return recordMap;
	}


	public Object getValue(String key) {
		return this.recordMap.get(key);
	}

}
