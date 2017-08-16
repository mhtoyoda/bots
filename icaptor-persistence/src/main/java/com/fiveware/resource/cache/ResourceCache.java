package com.fiveware.resource.cache;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiveware.cache.CacheManager;

@RestController
@RequestMapping("/api/cache")
public class ResourceCache {

	@Autowired
	private CacheManager<String> cacheManager;
	
	@GetMapping("/add/key/{key}/value/{value}")
	public void add(@PathVariable("key") String key, @PathVariable("value") String value) {
		cacheManager.add(key, value);
	}

	@GetMapping("/remove/key/{key}/value/{value}")
	public void remove(@PathVariable("key") String key, @PathVariable("value") String value) {
		cacheManager.remove(key, value);
	}

	@GetMapping("/get/{key}")
	public Set<String> getValues(@PathVariable("key") String key) {
		return cacheManager.getValues(key);
	}
	
	@GetMapping("/has/{key}")
	public boolean hasValue(@PathVariable("key") String key) {
		return cacheManager.hasValue(key);
	}
}