package com.oyr;

import com.oyr.shiro.jedis.JedisClientPool;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Create by 欧阳荣
 * 2018/6/2 18:20
 */
public class JedisTest {

    @Test
    public void test1(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext-jedis.xml");
        JedisClientPool bean = applicationContext.getBean(JedisClientPool.class);
        //bean.set("name".getBytes(), "oyr".getBytes());
    }
}
