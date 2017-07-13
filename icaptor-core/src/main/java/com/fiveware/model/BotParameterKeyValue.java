package com.fiveware.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class BotParameterKeyValue {
	
	private Map<String, String> keyValueMap;

	public BotParameterKeyValue add(String key, String value) {
		if( null == keyValueMap ){
			keyValueMap = new LinkedHashMap<>();
		}
		keyValueMap.put(key, value);
		return this;
	}

	public Map<String, String> getKeyValueMap() {
		return keyValueMap;
	}
}
