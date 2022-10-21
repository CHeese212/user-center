package com.zjh.usercenter.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用返回类
 *
 * @param <T>
 * @author cheese
 */
@Data
public class BaseResponse<T> implements Serializable {

    private static final long serialVersionUID = 9115618742647782902L;

    private int code;

    private T data;

    private String message;

    private String description;

    public BaseResponse() {
    }

    public BaseResponse(int code, T data) {
        this(code, data, "", "");
    }

    public BaseResponse(int code, T data, String message) {
        this(code, data, message, "");
    }

    public BaseResponse(int code, T data, String message, String description) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.description = description;
    }

    public BaseResponse(ErrorCode e) {
        this(e.getCode(), null, e.getMessage(), e.getDescription());
    }
}
