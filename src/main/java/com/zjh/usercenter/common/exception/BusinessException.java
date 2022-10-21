package com.zjh.usercenter.common.exception;

import com.zjh.usercenter.common.ErrorCode;
import lombok.Data;
import lombok.Getter;

/**
 * 自定义异常
 *
 * @author cheese
 */
@Getter
public class BusinessException extends RuntimeException {

    private final int code;

    private final String description;

    public BusinessException(String message, int code, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }

    public BusinessException(ErrorCode e) {
        super(e.getMessage());
        this.code = e.getCode();
        this.description = e.getDescription();
    }

    public BusinessException(ErrorCode e, String description) {
        super(e.getMessage());
        this.code = e.getCode();
        this.description = description;
    }
}
