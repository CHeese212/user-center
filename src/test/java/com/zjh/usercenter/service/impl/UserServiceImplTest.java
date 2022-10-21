package com.zjh.usercenter.service.impl;
import java.util.Date;

import com.zjh.usercenter.pojo.User;
import com.zjh.usercenter.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    @Resource
    private UserService userService;

    @Test
    void test01() {
        userService.userRegister("1234567", "2225555555555", "311444155555555");
    }

    @Test
    void userRegister() {
        //正常
        String userAccount = "cheese";
        String password = "12345678";
        String checkPassword = "12345678";
        long id = userService.userRegister(userAccount, password, checkPassword);
        Assertions.assertNotEquals(-1, id, userAccount + password + checkPassword + "创建成功");

        //非空
        userAccount = "";
        password = "12345678";
        checkPassword = "12345678";
        id = userService.userRegister(userAccount, password, checkPassword);
        Assertions.assertEquals(-1, id, "非空测试成功");

        //账户长度不小于4位
        userAccount = "123";
        password = "123456";
        checkPassword = "12345678";
        id = userService.userRegister(userAccount, password, checkPassword);
        Assertions.assertEquals(-1, id, "账户长度不小于4位测试成功");

        //密码不小于8位
        userAccount = "cheese";
        password = "1234567";
        checkPassword = "12345678";
        id = userService.userRegister(userAccount, password, checkPassword);
        Assertions.assertEquals(-1, id, "密码不小于8位测试成功");

        //账户不能重复
        userAccount = "123456";
        password = "12345678";
        checkPassword = "12345678";
        id = userService.userRegister(userAccount, password, checkPassword);
        Assertions.assertEquals(-1, id, "账户不能重复测试成功");

        //账户不包含特殊字符
        userAccount = "~*/-+?;";
        password = "12345678";
        checkPassword = "12345678";
        id = userService.userRegister(userAccount, password, checkPassword);
        Assertions.assertEquals(-1, id, "账户不包含特殊字符测试成功");

        //密码和校验密码相同
        userAccount = "cheese";
        password = "12345679";
        checkPassword = "12345678";
        id = userService.userRegister(userAccount, password, checkPassword);
        Assertions.assertEquals(-1, id, "密码和校验密码相同测试成功");
    }
}