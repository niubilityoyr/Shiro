package com.oyr.shiro.realms;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Create by 欧阳荣
 * 2018/6/1 23:02
 */
public class AuthRealm extends AuthorizingRealm {

    Logger log = LoggerFactory.getLogger(this.getClass());

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("开始授权---------------------");
        String username = (String) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRole("user");
        if(username.equals("oyr")){
            info.addRole("admin");
        }
        info.addStringPermission("user:delete");
        return info;
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("开始认证-------------------------");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        //根据用户名从数据库取数据
        if(username.equals("null")){
            throw new UnknownAccountException("用户不存在");
        }
        ByteSource salt = ByteSource.Util.bytes(username);
        return new SimpleAuthenticationInfo(username, "0a595d7024fcb5cfa48ea2988acdd350", salt, this.getName());
    }

    public static void main(String[] args) {
        String algorithmName = "MD5";
        Object source = "123";
        ByteSource salt = ByteSource.Util.bytes("oyr");
        int hashIterations = 1024;
        SimpleHash hash = new SimpleHash(algorithmName, source, salt, hashIterations);
        System.out.println(hash);
    }
}
