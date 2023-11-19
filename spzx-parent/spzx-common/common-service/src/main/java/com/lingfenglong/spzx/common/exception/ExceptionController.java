package com.lingfenglong.spzx.common.exception;

import com.lingfenglong.spzx.model.vo.common.CommonResultCode;
import com.lingfenglong.spzx.model.vo.common.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(Exception.class)
    public Result<?> unknownGlobalExceptionHandler(Exception e) {
        e.printStackTrace();
        return Result.build(null, CommonResultCode.UNKNOWN_GLOBAL_EXCEPTION.getCode(), Arrays.toString(e.getStackTrace()));
    }

    @ExceptionHandler(SysUserException.class)
    public Result<?> sysUserExceptionHandler(SysUserException e) {
        e.printStackTrace();
        return Result.build(null, e.getSysUserResultCode());
    }
}
