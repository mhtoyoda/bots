package com.fiveware.file;

import java.util.LinkedHashMap;
import java.util.Map;

public class RecordLine {

	private Map<String, String> recordMap;

	public RecordLine() {
		if (recordMap == null) {
			recordMap = new LinkedHashMap<String, String>();
		}
	}

	public void addRecordLine(String field, String value) {
		recordMap.put(field, value);
	}

	public Map<String, String> getRecordMap() {
		return recordMap;
	}
}
