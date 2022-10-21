package com.zjh.usercenter.pojo.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 *
 * @author cheese
 */
@Data
public class UserLoginRequest implements Serializable {
    private static final long serialVersionUID = 5470100555409131865L;
    private String userAccount;
    private String userPassword;
}
