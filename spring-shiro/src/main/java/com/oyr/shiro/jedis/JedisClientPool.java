package com.oyr.shiro.jedis;

import com.oyr.shiro.utils.SerializeUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Create by 欧阳荣
 * 2018/6/2 17:37
 */
public class JedisClientPool {

    private JedisPool jedisPool;

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public void set(byte[] key, byte[] val, int expireTime){
        Jedis jedis = jedisPool.getResource();
        jedis.set(key, val);
        jedis.expire(key,expireTime);
        jedis.close();
    }

    public Object get(byte[] key){
        Jedis jedis = jedisPool.getResource();
        byte[] val = jedis.get(key);
        jedis.close();
        if(val==null || val.length<=0){
            return null;
        }
        return SerializeUtil.deserializeObject(val);
    }

    public void del(byte[] key){
        Jedis jedis = jedisPool.getResource();
        jedis.del(key);
        jedis.close();
    }

    public void flushDB(){
        Jedis jedis = jedisPool.getResource();
        jedis.flushDB();
        jedis.close();
    }

    public Long dbSize(){
        Jedis jedis = jedisPool.getResource();
        Long size = jedis.dbSize();
        jedis.close();
        return size;
    }
}
