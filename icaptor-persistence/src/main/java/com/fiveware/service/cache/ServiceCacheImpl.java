package com.fiveware.service.cache;

import com.fiveware.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;
import java.util.Set;

@Service
public class ServiceCacheImpl {

    @Autowired
    private final CacheManager<String> cacheManager;

    public ServiceCacheImpl(CacheManager<String> cacheManager) {
        this.cacheManager = cacheManager;
    }

    public Boolean add(Map<String,Set<String>> cache) {
        String key = cache.keySet().iterator().next();
        Set<String> o = cache.get(key);
        return cacheManager.add(key, o.iterator().next());
    }

    public Boolean remove(Map<String,Set<String>> cache) {
        String key = cache.keySet().iterator().next();
        Set<String> o = cache.get(key);
        return cacheManager.remove(key, o.iterator().next());
    }

    public Set<Map.Entry<String, Set<String>>> list() {
        return cacheManager.list();
    }

    public Set<String> getValues(@PathVariable("key") String key) {
        return cacheManager.getValues(key);
    }

    public boolean hasValue(@PathVariable("key") String key) {
        return cacheManager.hasValue(key);
    }

    public Boolean hmAdd(@RequestBody Map<String, Map<String, Set<String>>> cache) {
        String key = cache.keySet().iterator().next();
        Map<String, Set<String>> map = cache.get(key);
        String keyValue = map.keySet().iterator().next();
        Set<String> value = map.get(keyValue);
        return cacheManager.add(key, keyValue, value.iterator().next());
    }

    public Boolean hmRemove(@RequestBody Map<String, Map<String, Set<String>>> cache) {
        String key = cache.keySet().iterator().next();
        Map<String, Set<String>> map = cache.get(key);
        String keyValue = map.keySet().iterator().next();
        Set<String> value = map.get(keyValue);
        return cacheManager.remove(key, keyValue, value.iterator().next());
    }

    public Set<Map.Entry<String, Map<String, Set<String>>>> map() {
        return cacheManager.map();
    }


    public Set<String> hmGetValues(String key, String keyValue) {
        return cacheManager.getValues(key,keyValue);
    }

    public boolean hmHasValue(String key, String keyValue) {
        return cacheManager.hasValue(key,keyValue);
    }
}
