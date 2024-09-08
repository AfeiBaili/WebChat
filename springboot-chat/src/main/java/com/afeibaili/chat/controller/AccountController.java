package com.afeibaili.chat.controller;

import com.afeibaili.chat.pojo.Account;
import com.afeibaili.chat.pojo.Result;
import com.afeibaili.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * @author AfeiB
 * @description 账户控制类
 * @createDate 2024/8/14 下午10:13
 */
@Controller
@CrossOrigin
public class AccountController {

    @Autowired
    private UserService userService;

    @PostMapping("login")
    @ResponseBody
    public Result login(@RequestBody Account account) {
        Date date = new Date();
        System.out.println("[" + date + "]\t登录请求: 登录名=" + account.getName() + ",登录id=" + account.getId());
        return userService.loginUser(account);
    }

    @PostMapping("register")
    @ResponseBody
    public Result register(@RequestBody Account account) {
        Date date = new Date();
        System.out.println("[" + date + "]\t注册请求: 注册名=" + account.getName() + ",注册id=" + account.getId());
        return userService.registerUser(account);
    }

    @PostMapping("update")
    @ResponseBody
    public Result updateUser(@RequestBody Account account) {
        return userService.updateUser(account);
    }
}
