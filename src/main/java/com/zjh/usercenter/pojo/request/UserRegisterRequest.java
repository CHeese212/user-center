package com.zjh.usercenter.pojo.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 *
 * @author cheese
 */
@Data
public class UserRegisterRequest implements Serializable {
    private static final long serialVersionUID = 7135612822525212930L;

    private String userAccount;
    private String userPassword;
    private String checkPassword;
}
