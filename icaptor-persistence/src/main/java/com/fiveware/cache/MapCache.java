package com.fiveware.cache;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;

import com.google.common.collect.Sets;

@Component("mapCache")
public class MapCache implements CacheManager<String> {
	
	private static Map<String,Set<String>> cache = new LinkedHashMap<String,Set<String>>();
	private static Map<String, Map<String, String>> cacheMap = new HashMap<String, Map<String,String>>();
	
	@Override
	public boolean add(String key, String value) {
		boolean add = cache.computeIfAbsent(key, values -> new HashSet<>()).add(value);
		return add;
	}
	
	@Override
	public boolean add(String key, String keyValue, String value) { 
		cacheMap.computeIfAbsent(key, queues -> new HashMap<String,String>()).put(keyValue, value);
		return true;
	}
	
	@Override
	public boolean remove(String key, String value) {
		boolean removeIf = false;
		if(cache.containsKey(key)){
			Predicate<String> filter = p-> p.equals(value);
			removeIf = cache.get(key).removeIf(filter);
		}
		return removeIf;
	}
	
	@Override
	public boolean remove(String key, String keyValue, String value) {
		boolean removeIf = false;
		if(cacheMap.containsKey(key)){
			Map<String, String> mapValues = cacheMap.get(key);
			if(mapValues.containsKey(keyValue)){
				removeIf = mapValues.entrySet().removeIf(k -> k.equals(keyValue));								
			}
		}
		return removeIf;
	}
	
	@Override
	public Set<String> getValues(String key) {
		return cache.get(key) == null ? Sets.newHashSet() : cache.get(key);
	}
	
	@Override
	public String getValues(String key, String keyValue) {
		if(cacheMap.get(key) == null){
			return "";
		}
		Map<String, String> map = cacheMap.get(key);
		return map.get(keyValue) == null ? "" : map.get(keyValue);		
	}

	@Override
	public Set<Map.Entry<String, Set<String>>> list() {
		return cache.entrySet();
	}

	@Override
	public Set<Entry<String, Map<String, String>>> map() {
		return cacheMap.entrySet();
	}
	
	@Override
	public boolean hasValue(String key) {
		return cache.get(key) != null;
	}	
	
	@Override
	public boolean hasValue(String key, String keyValue) {		
		boolean hasValue = false;
		if(cacheMap.get(key) == null){
			return hasValue;
		}
		Map<String, String> map = cacheMap.get(key);
		return map.get(keyValue) == null ? hasValue : true;
	}
}