package com.oyr.shiro.cache;

import com.oyr.shiro.jedis.JedisClientPool;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Create by 欧阳荣
 * 2018/6/2 20:43
 */
public class RedisCacheManager implements CacheManager {

    Logger log = LoggerFactory.getLogger(this.getClass());

    private JedisClientPool jedisClientPool;

    //map，先查map中是否有缓存，如果没有从redis中取
    private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<String, Cache>();

    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        log.debug("获取名称为: " + name + " 的RedisCache实例");
        Cache cache = caches.get(name);
        if (cache == null) {//如果map中没有这个缓存
            //新建一个缓存
            cache = new RedisCache<K, V>(jedisClientPool);
            //加入到map中
            caches.put(name, cache);
        }
        return cache;
    }

    public JedisClientPool getJedisClientPool() {
        return jedisClientPool;
    }

    public void setJedisClientPool(JedisClientPool jedisClientPool) {
        this.jedisClientPool = jedisClientPool;
    }
}
