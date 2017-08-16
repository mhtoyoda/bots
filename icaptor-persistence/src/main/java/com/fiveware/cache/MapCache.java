package com.fiveware.cache;

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
	
	@Override
	public boolean add(String key, String value) {
		boolean add = cache.computeIfAbsent(key, queues -> new HashSet<>()).add(value);
		return add;
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
	public Set<String> getValues(String key) {
		return cache.get(key) == null ? Sets.newHashSet() : cache.get(key);
	}

	@Override
	public boolean hasValue(String key) {
		return cache.get(key) != null;
	}

}
