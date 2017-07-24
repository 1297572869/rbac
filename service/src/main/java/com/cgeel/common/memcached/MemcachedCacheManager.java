package com.cgeel.common.memcached;

import net.rubyeye.xmemcached.MemcachedClient;
import org.springframework.cache.Cache;
import org.springframework.cache.transaction.AbstractTransactionSupportingCacheManager;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by zxw on 2015/7/4.
 */
public class MemcachedCacheManager extends AbstractTransactionSupportingCacheManager {

    private ConcurrentMap<String, Cache> cacheMap = new ConcurrentHashMap<>();
    private Map<String, Integer> expireMap = new HashMap<>();
    private Map<String, MemcachedClient> memcachedClientMap = new HashMap<>();

    public MemcachedCacheManager() {
    }

    @Override
    protected Collection<? extends Cache> loadCaches() {
        return cacheMap.values();
    }

    @Override
    public Cache getCache(String name) {
        Cache cache = cacheMap.get(name);
        if (cache == null) {
            Integer expire = expireMap.get(name);
            if (expire == null) {
                expire = 0;
                expireMap.put(name, expire);
            }
            MemcachedClient memcachedClient = memcachedClientMap.get(name);
            if(memcachedClient == null){
                throw new IllegalArgumentException("memcachedClient is null");
            }
            cache = new MemcachedCache(name, expire.intValue(), memcachedClient);
            cacheMap.put(name, cache);
        }
        return cache;
    }

    public void setConfigMap(Map<String, Integer> configMap) {
        this.expireMap = configMap;
    }

    public void setMemcachedClientMap(Map<String, MemcachedClient> memcachedClientMap) {
        this.memcachedClientMap = memcachedClientMap;
    }
}
