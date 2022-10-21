package com.zjh.usercenter.service;

import com.zjh.usercenter.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author cheese
 * @description 针对表【user(用户)】的数据库操作Service
 * @createDate 2022-10-09 21:39:52
 */
public interface UserService extends IService<User> {
    /**
     * 用户注册
     *
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @return 用户id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * 用户登陆
     *
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @return 登陆成功返回登录的User对象(脱敏)，登陆失败返回null
     */
    User doLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户脱敏
     *
     * @param user 初始的用户
     * @return 脱敏后的用户
     */
    User getSafetyUser(User user);

    /**
     * 用户登出
     *
     * @return 返回用户id
     */
    void doLogout(HttpServletRequest request);
}
