package com.fiveware.cache;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;

import com.google.common.collect.Sets;

@Component("mapCache")
public class MapCache implements CacheManager<String> {
	
	private static Map<String,Set<String>> cache = new LinkedHashMap<String,Set<String>>();
	private static Map<String, Map<String, Set<String>>> cacheMap = new HashMap<String, Map<String,Set<String>>>();
	
	@Override
	public boolean add(String key, String value) {
		boolean add = cache.computeIfAbsent(key, values -> new HashSet<>()).add(value);
		return add;
	}
	
	@Override
	public boolean add(String key, String keyValue, String value) {
		if(cacheMap.get(key) == null){
			Map<String, Set<String>> map = new HashMap<>();
			map.put(keyValue, Sets.newHashSet(value));
			cacheMap.put(key, map);
		}else{
			Map<String, Set<String>> map = cacheMap.get(key);
			if(map.containsKey(keyValue)){
				Set<String> values = map.get(keyValue);
				values.add(value);
			}else{
				Set<String> values = Sets.newHashSet(value);
				map.put(keyValue, values);
			}
			cacheMap.put(key, map);
		}
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
			Map<String, Set<String>> map = cacheMap.get(key);
			if(map.containsKey(keyValue)){
				Set<String> cache = map.get(keyValue);
				Predicate<String> filter = p-> p.equals(value);
				removeIf = cache.removeIf(filter);								
			}
		}
		return removeIf;
	}
	
	@Override
	public Set<String> getValues(String key) {
		return cache.get(key) == null ? Sets.newHashSet() : cache.get(key);
	}
	
	@Override
	public Set<String> getValues(String key, String keyValue) {
		if(cacheMap.get(key) == null){
			return Sets.newHashSet();
		}
		Map<String, Set<String>> cache = cacheMap.get(key);
		return cache.get(keyValue) == null ? Sets.newHashSet() : cache.get(keyValue);				
	}

	@Override
	public Set<Map.Entry<String, Set<String>>> list() {
		return cache.entrySet();
	}

	@Override
	public Set<Map.Entry<String,Map<String,Set<String>>>> map() {
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
		return cacheMap.get(key).get(keyValue) == null ? hasValue : true;
	}
}