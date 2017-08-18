package com.fiveware.resource.cache;

import com.fiveware.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/cache")
public class ResourceCache {

	@Autowired
	private CacheManager<String> cacheManager;
	
	@GetMapping("/add/key/{key}/value/{value}")
	public void add(@PathVariable("key") String key, @PathVariable("value") String value) {
		cacheManager.add(key, value);
	}


	@PostMapping
	public Boolean add(@RequestBody Map<String,Set<String>> cache) {
		String key = cache.keySet().iterator().next();
		Set<String> o = cache.get(key);
		return cacheManager.add(key, o.iterator().next());
	}


	@DeleteMapping
	public Boolean remove(@RequestBody Map<String,Set<String>> cache) {
		String key = cache.keySet().iterator().next();
		Set<String> o = cache.get(key);
		return cacheManager.remove(key, o.iterator().next());
	}

	@GetMapping
	public Set<Map.Entry<String, Set<String>>> list() {
		return cacheManager.list();
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