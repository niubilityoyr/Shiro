package com.oyr.shiro.factory;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Create by 欧阳荣
 * 2018/6/2 16:07
 */
public class FilterChainDefinitionMapProxyFactory {

    public Map FilterChainDefinitionMap(){
        Map<String, String> map = new LinkedHashMap<String, String>();
        //注意：这是有顺序的
        map.put("/login_page.htm", "anon");
        map.put("/login.htm", "anon");
        map.put("/user.htm", "roles[user]");
        map.put("/admin.htm", "roles[admin]");
        map.put("/**", "authc");
        return map;
    }

}
