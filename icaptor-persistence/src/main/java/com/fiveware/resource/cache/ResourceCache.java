package com.fiveware.resource.cache;

import java.util.Map;
import java.util.Set;

import com.fiveware.service.cache.ServiceCacheImpl;
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
    private ServiceCacheImpl serviceCache;

    @PostMapping
    public Boolean add(@RequestBody Map<String, Set<String>> cache) {
        return serviceCache.add(cache);
    }

    @DeleteMapping
    public Boolean remove(@RequestBody Map<String, Set<String>> cache) {
        return serviceCache.remove(cache);
    }

    @GetMapping
    public Set<Map.Entry<String, Set<String>>> list() {
        return serviceCache.list();
    }

    @GetMapping("/get/{key}")
    public Set<String> getValues(@PathVariable("key") String key) {
        return serviceCache.getValues(key);
    }

    @GetMapping("/has/{key}")
    public boolean hasValue(@PathVariable("key") String key) {
        return serviceCache.hasValue(key);
    }

    @PostMapping("/hm")
    public Boolean hmAdd(@RequestBody Map<String, Map<String, Set<String>>> cache) {
        return serviceCache.hmAdd(cache);
    }

    @DeleteMapping("/hm")
    public Boolean hmRemove(@RequestBody Map<String, Map<String, Set<String>>> cache) {
        return serviceCache.hmRemove(cache);
    }

    @GetMapping("/hm")
    public Set<Map.Entry<String, Map<String, Set<String>>>> map() {
        return serviceCache.map();
    }

    @GetMapping("/hm/get/{key}/{keyValue}")
    public Set<String> hmGetValues(@PathVariable("key") String key, @PathVariable("keyValue") String keyValue) {
        return serviceCache.hmGetValues(key, keyValue);
    }

    @GetMapping("/hm/has/{key}/{keyValue}")
    public boolean hmHasValue(@PathVariable("key") String key, @PathVariable("keyValue") String keyValue) {
        return serviceCache.hmHasValue(key, keyValue);
    }
}