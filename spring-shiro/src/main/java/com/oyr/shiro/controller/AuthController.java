package com.oyr.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Create by 欧阳荣
 * 2018/6/1 23:09
 */
@Controller
public class AuthController {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/unauthorized")
    public String unauthorized(){
        return "/WEB-INF/view/unauthorized";
    }

    @RequestMapping("/login_page")
    public String login_page(){
        return "login";
    }

    @RequestMapping("/login")
    @ResponseBody
    public String login(String username, String password){
        Subject subject = SecurityUtils.getSubject();
        //先判断是否认证过
        if(!subject.isAuthenticated()){
            //没有登录开始登录
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            //执行登录
            try{
                subject.login(token);
            }catch(IncorrectCredentialsException e){
                e.printStackTrace();
                log.info("密码错误");
                return "mmcw";
            }catch (UnknownAccountException e){
                e.printStackTrace();
                log.info("账号不存在");
                return "zhbcz";
            } catch(AuthenticationException e){
                e.printStackTrace();
                log.info("未知错误");
                return "wzcw";
            }
        }else{
            return "ydl";
        }
        return "cg";
    }

    @RequestMapping("/list")
    public String to_list() {
        return "list";
    }

    @RequestMapping("/user")
    public String showUser(){
        return "user";
    }

    @RequestMapping("/admin")
    public String showAdmin(){
        return "admin";
    }

    @RequestMapping("/showUserName")
    @ResponseBody
    public String showUserName(){
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        return username;
    }
}
