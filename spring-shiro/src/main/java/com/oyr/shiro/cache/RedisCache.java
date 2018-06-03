package com.oyr.shiro.cache;

import com.oyr.shiro.jedis.JedisClientPool;
import com.oyr.shiro.utils.SerializeUtil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Set;

/**
 * Create by 欧阳荣
 * 2018/6/2 20:25
 */
public class RedisCache<K,V> implements Cache<K,V> {

    //缓存在redis中的前缀
    private String PRI_KEY = "shiro_redis_session:";

    private int expireTime = 1800;

    private JedisClientPool jedisClientPool;

    public RedisCache(JedisClientPool jedisClientPool) {
        this.jedisClientPool = jedisClientPool;
    }

    Logger log = LoggerFactory.getLogger(this.getClass());

    public byte[] getKey(Object object){
        String key = PRI_KEY + object;
        return key.getBytes();
    }

    public Object get(Object o) throws CacheException {
        log.info("从redis获取缓存------------------------");
        byte[] key = getKey(o);
        Object val = jedisClientPool.get(key);
        return val;
    }

    public Object put(Object key, Object value) throws CacheException {
        log.info("往redis中存入缓存------------------------");
        byte[] key1 = getKey(key);
        byte[] bytes = SerializeUtil.serializeObject(value);
        jedisClientPool.set(key1, bytes, expireTime);
        Object result = jedisClientPool.get(key1);
        return result;
    }

    public Object remove(Object key) throws CacheException {
        log.info("删除redis中缓存数据------------------------");
        byte[] key1 = getKey(key);
        Object value = jedisClientPool.get(key1);
        jedisClientPool.del(key1);
        return value;
    }

    //清空所有缓存
    public void clear() throws CacheException {
        jedisClientPool.flushDB();
    }

    //一共有几个缓存
    public int size() {
        Long size = jedisClientPool.dbSize();
        return Integer.parseInt(size + "");
    }

    public Set keys() {

        return null;
    }

    public Collection values() {
        return null;
    }
}
