package com.fiveware.resource.cache;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiveware.cache.CacheManager;

@RestController
@RequestMapping("/api/cache")
public class ResourceCache {

	@Autowired
	private CacheManager<String> cacheManager;
	
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
	
	@PostMapping("/hm")
	public Boolean hmAdd(@RequestBody Map<String, Map<String, String>> cache) {
		String key = cache.keySet().iterator().next();
		Map<String, String> map = cache.get(key);
		String keyValue = map.keySet().iterator().next();
		String value = map.get(keyValue);
		return cacheManager.add(key, keyValue, value);
	}

	@DeleteMapping("/hm")
	public Boolean hmRemove(@RequestBody Map<String, Map<String, String>> cache) {
		String key = cache.keySet().iterator().next();
		Map<String, String> map = cache.get(key);
		String keyValue = map.keySet().iterator().next();
		String value = map.get(keyValue);
		return cacheManager.remove(key, keyValue, value);
	}

	@GetMapping("/hm")
	public Set<Entry<String, Map<String, String>>> map() {
		return cacheManager.map() ;
	}

	@GetMapping("/hm/get/{key}/{keyValue}")
	public String hmGetValues(@PathVariable("key") String key, @PathVariable("keyValue") String keyValue) {
		return cacheManager.getValues(key, keyValue);
	}
	
	@GetMapping("/hm/has/{key}/{keyValue}")
	public boolean hmHasValue(@PathVariable("key") String key, @PathVariable("keyValue") String keyValue) {
		return cacheManager.hasValue(key, keyValue);
	}
}