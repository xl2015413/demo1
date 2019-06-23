package com.qphone.controller;

import com.qphone.pojo.Member;
import com.qphone.service.MemberService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by asus on 2019/6/3.
 */
@Controller
public class MemberController {
    @RequestMapping("/checklogin")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password){
        System.out.print(username+password);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            //执行认证操作.
            subject.login(token);
        }catch (AuthenticationException ae) {
            System.out.println("登陆失败: " + ae.getMessage());
            return "/login";
        }

        return "/index";
    }

}
