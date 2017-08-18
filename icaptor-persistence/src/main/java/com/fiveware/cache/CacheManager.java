package com.fiveware.cache;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public interface CacheManager<T>{

	boolean add(String key, T value);
		
	boolean remove(String key, T value);
	
	Set<String> getValues(String key);

    Set<Map.Entry<String, Set<String>>> list();

    boolean hasValue(String key);

	boolean add(String key, String keyValue, String value);

	boolean remove(String key, String keyValue, String value);

	String getValues(String key, String keyValue);

	Set<Entry<String, Map<String, String>>> map();

	boolean hasValue(String key, String keyValue);
}
