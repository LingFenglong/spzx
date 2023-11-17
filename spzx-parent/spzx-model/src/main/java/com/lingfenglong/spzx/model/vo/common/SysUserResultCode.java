package com.lingfenglong.spzx.model.vo.common;

import lombok.Getter;

@Getter
public enum SysUserResultCode implements ResultCode {
    LOGIN_SUCCESS(200, "登录成功"),
    LOGOUT_SUCCESS(200, "登出成功"),
    LOGIN_ERROR(201, "用户名或者密码错误"),
    VALIDATE_CODE_ERROR(202, "验证码错误"),
    LOGIN_AUTH(401, "用户未登录"),
    USER_NAME_IS_EXISTS(209, "用户名已经存在"),
    ACCOUNT_STOP(216, "账号已停用");

    private final Integer code;      // 业务状态码
    private final String message;    // 响应消息

    SysUserResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
