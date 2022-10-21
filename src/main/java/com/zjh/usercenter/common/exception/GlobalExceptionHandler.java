package com.zjh.usercenter.common.exception;

import com.zjh.usercenter.common.BaseResponse;
import com.zjh.usercenter.common.ErrorCode;
import com.zjh.usercenter.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @author cheese
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public BaseResponse businessExceptionHandler(BusinessException e) {
        log.error("businessException:", e);
        return ResponseUtil.error(e.getCode(), e.getMessage(), e.getDescription());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse runtimeExceptionHandler(RuntimeException e) {
        log.error("runtimeException:", e);
        return ResponseUtil.error(ErrorCode.SYSTEM_ERROR, e.getMessage());
    }
}
