package com.oyr.shiro.filter;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

/**
 * Create by 欧阳荣
 * 2018/6/2 16:53
 */
public class ShiroSessionListener implements SessionListener {
    public void onStart(Session session) {
        System.out.println("session开启了" + session.getId());
    }

    public void onStop(Session session) {
        System.out.println("session关闭了" + session.getId());
    }

    public void onExpiration(Session session) {
        System.out.println("session过期了" + session.getId());
    }
}
