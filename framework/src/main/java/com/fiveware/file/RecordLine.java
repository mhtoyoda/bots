package com.fiveware.file;

import java.util.LinkedHashMap;
import java.util.Map;

public class RecordLine {

	private Map<String, String> recordMap = new LinkedHashMap<String, String>();

	public RecordLine() {
	}

	public RecordLine(Map<String, String> recordMap) {
		this.recordMap = recordMap;
	}

	public synchronized void addRecordLine(String field, String value) {
		recordMap.put(field, value);
	}

	public synchronized Map<String, String> getRecordMap() {
		return recordMap;
	}


	public String getValue(String key) {
		return this.recordMap.get(key);
	}

}
