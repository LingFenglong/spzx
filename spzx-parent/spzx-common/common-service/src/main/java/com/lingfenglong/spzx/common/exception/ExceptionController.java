package com.lingfenglong.spzx.common.exception;

import com.lingfenglong.spzx.model.vo.common.CommonResultCode;
import com.lingfenglong.spzx.model.vo.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionController {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Result<?> unknownGlobalExceptionHandler(Exception e) {
        return Result.build(null, CommonResultCode.UNKNOWN_GLOBAL_EXCEPTION);
    }

    @ResponseBody
    @ExceptionHandler(SysUserException.class)
    public Result<?> sysUserExceptionHandler(SysUserException e) {
        return Result.build(null, e.getSysUserResultCode());
    }
}
