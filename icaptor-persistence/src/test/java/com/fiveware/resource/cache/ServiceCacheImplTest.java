package com.fiveware.resource.cache;

import com.fiveware.cache.CacheManager;
import com.fiveware.service.cache.ServiceCacheImpl;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;


public class ServiceCacheImplTest {


    @Mock
    CacheManager cacheManager;

    ServiceCacheImpl serviceCache;

    Map<String, Set<String>> cache;
    private String key;
    private Set<String> values;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        cache = spy(Maps.newHashMap());
        key = "teste";
        values = Sets.newHashSet("value");
        cache = spy(Maps.newHashMap());
        cache.put(key, values);

        this.serviceCache = new ServiceCacheImpl(cacheManager);
    }

    @Test
    public void add() throws Exception {
        when(cacheManager.add(cache.keySet().iterator().next(), values.iterator().next())).thenReturn(true);

        assertEquals(serviceCache.add(cache), true);

        verify(cache, times(1)).get(key);
        verify(cache, times(2)).keySet();
        verify(cacheManager, times(1)).add(key, values.iterator().next());
    }

    @Test
    public void remove() throws Exception {
        when(cacheManager.remove(cache.keySet().iterator().next(), values.iterator().next())).thenReturn(true);
        assertEquals(serviceCache.remove(cache), true);

        verify(cache, times(1)).get(key);
        verify(cache, times(2)).keySet();
        verify(cacheManager, times(1)).remove(key, values.iterator().next());
    }

    @Test
    public void list() throws Exception {

        Set<Map.Entry<String, Set<String>>> cacheElem = cache.entrySet();
        when(cacheManager.list()).thenReturn(cacheElem);

        assertEquals(serviceCache.list().size(), 1);
        verify(cacheManager).list();
    }

    @Test
    public void getValues() throws Exception {
        when(cacheManager.getValues(key)).thenReturn(values);

        assertEquals(serviceCache.getValues(key).size(), 1);

        verify(cacheManager).getValues(key);
    }

    @Test
    public void hasValue() throws Exception {
        when(cacheManager.hasValue(key)).thenReturn(true);
        assertEquals(serviceCache.hasValue(key), true);
        verify(cacheManager).hasValue(key);
    }

    public void hmAdd() throws Exception {
        Map<String, Map<String, Set<String>>> _cache = Maps.newHashMap();
        _cache.put("teste2", cache);

//        when(_cache.get("teste2")).thenReturn(cache);

        when(cacheManager.add("teste2", "teste", cache.keySet().iterator().next())).thenReturn(true);

        assertEquals(serviceCache.hmAdd(_cache), true);

    }

    @Test
    public void hmRemove() throws Exception {

    }

    @Test
    public void map() throws Exception {
    }

    @Test
    public void hmGetValues() throws Exception {

        Set<String> values = cache.keySet();
        when(cacheManager.getValues(key,"teste2")).thenReturn(values);

        assertEquals(serviceCache.hmGetValues(key,"teste2").size(),1);
        verify(cacheManager,times(1)).getValues(key,"teste2");
    }

    @Test
    public void hmHasValue() throws Exception {
        when(cacheManager.hasValue(key,"teste2")).thenReturn(true);

        assertTrue(serviceCache.hmHasValue(key,"teste2"));

        verify(cacheManager).hasValue(key,"teste2");
    }

}