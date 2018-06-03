package com.oyr.shiro.session;

import com.oyr.shiro.jedis.JedisClientPool;
import com.oyr.shiro.utils.SerializeUtil;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Collection;

/**
 * Create by 欧阳荣
 * 2018/6/2 17:25
 */
public class RedisSessionDao extends AbstractSessionDAO {

    Logger log = LoggerFactory.getLogger(this.getClass());

    private String PRI_KEY = "redis_shiro_session:";//redis中会话的前缀

    private int expireTime = 1800;//会话在redis中的过期时间

    private JedisClientPool jedisClientPool;

    public JedisClientPool getJedisClientPool() {
        return jedisClientPool;
    }

    public void setJedisClientPool(JedisClientPool jedisClientPool) {
        this.jedisClientPool = jedisClientPool;
    }

    //创建session
    protected Serializable doCreate(Session session) {
        log.info("dao ---------------create session");
        Serializable sessionId = this.generateSessionId(session);//生成会话id，可以设置生成策略
        this.assignSessionId(session, sessionId);//把sessionId设置进session中
        this.saveSession(session);//保存到redis中
        return sessionId;
    }

    //读取session
    protected Session doReadSession(Serializable serializable) {
        log.info("dao ---------------read session");
        if(serializable==null)
            return null;
        Session session = (Session) jedisClientPool.get(getKey(serializable));
        return session;
    }

    //修改session
    public void update(Session session) throws UnknownSessionException {
        log.info("dao ---------------update session");
        this.saveSession(session);
    }

    //删除session
    public void delete(Session session) {
        log.info("dao ---------------delete session");
        if(session==null || session.getId()==null){
            return;
        }
        jedisClientPool.del(getKey(session.getId()));
    }

    public Collection<Session> getActiveSessions() {
        return null;
    }

    public byte[] getKey(Serializable serializable){
        String key = PRI_KEY + serializable;
        return key.getBytes();
    }

    public void saveSession(Session session){
        if(session==null || session.getId()==null){
            log.info("session or sessionId is null");
            return;
        }
        session.setTimeout(1800000l);
        //保存到redis中
        jedisClientPool.set(getKey(session.getId()), SerializeUtil.serializeObject(session), expireTime);
    }
}
