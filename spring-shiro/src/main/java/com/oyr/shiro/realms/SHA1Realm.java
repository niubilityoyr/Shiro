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
public class SHA1Realm extends AuthorizingRealm {

    Logger log = LoggerFactory.getLogger(this.getClass());

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info(this.getName() + "开始授权---------------------");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //info.addRole("admin");
        info.addStringPermission("user:delete");
        return info;
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info(this.getName() + "开始认证-------------------------");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        //根据用户名从数据库取数据
        if(username.equals("null")){
            throw new UnknownAccountException("用户不存在");
        }
        ByteSource salt = ByteSource.Util.bytes(username);
        return new SimpleAuthenticationInfo(username, "c0008f926bf0c6317f0a433997a11d845596afd0", salt, this.getName());
    }

    public static void main(String[] args) {
        String algorithmName = "SHA1";
        Object source = "123";
        ByteSource salt = ByteSource.Util.bytes("zyb");
        int hashIterations = 1024;
        SimpleHash hash = new SimpleHash(algorithmName, source, salt, hashIterations);
        System.out.println(hash);
    }
}
