package com.zjh.usercenter.utils;

import com.zjh.usercenter.common.BaseResponse;
import com.zjh.usercenter.common.ErrorCode;
import com.zjh.usercenter.common.exception.BusinessException;

public class ResponseUtil {
    /**
     * 成功
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(0, data, "ok");
    }

    /**
     * 失败
     *
     * @param e
     * @param <T>
     * @return
     */
    public static <T> BaseResponse<T> error(ErrorCode e) {
        return new BaseResponse<>(e);
    }

    /**
     * 失败
     *
     * @param code
     * @param message
     * @param description
     * @param <T>
     * @return
     */
    public static <T> BaseResponse<T> error(int code, String message, String description) {
        return new BaseResponse<>(code,null ,message, description);
    }

    /**
     * 失败
     *
     * @param e
     * @param <T>
     * @return
     */
    public static <T> BaseResponse<T> error(ErrorCode e, String description) {
        return new BaseResponse<>(e.getCode(), null, e.getMessage(), description);
    }

}
