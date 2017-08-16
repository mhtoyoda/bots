package com.fiveware.cache;

import java.util.Set;

public interface CacheManager<T>{

	public boolean add(String key, T value);
		
	public boolean remove(String key, T value);
	
	public Set<String> getValues(String key);
	
	public boolean hasValue(String key);
}
