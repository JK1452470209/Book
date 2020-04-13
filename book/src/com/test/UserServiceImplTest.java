package com.test;

import com.pojo.User;
import com.service.UserService;
import com.service.impl.UserServiceImpl;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @outhor Mr.JK
 * @create 2020-04-04  10:15
 */
public class UserServiceImplTest {
    UserService userService = new UserServiceImpl();

    @Test
    public void registUser() {
        userService.registUser(new User(null,"lin","123456","123@qq.com"));
    }

    @Test
    public void login() {
        System.out.println(userService.login(new User(null,"lin","123456","1")));
    }

    @Test
    public void existUsername() {
        if (userService.existUsername("lin")){
            System.out.println("用户名已存在！");
        }else {
            System.out.println("用户名可用!");
        }
    }
}