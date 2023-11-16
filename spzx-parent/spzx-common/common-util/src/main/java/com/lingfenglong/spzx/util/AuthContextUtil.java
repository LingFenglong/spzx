package com.lingfenglong.spzx.util;

import com.lingfenglong.spzx.model.entity.system.SysUser;
import lombok.Data;

/**
 * 将用户信息存储到ThreadLocal的工具类
 */

@Data
public class AuthContextUtil {
    private static final ThreadLocal<SysUser> threadLocal = new ThreadLocal<>();

    public static void set(SysUser sysUser) {
        threadLocal.set(sysUser);
    }

    public static SysUser get() {
        return threadLocal.get();
    }

    public static void remove() {
        threadLocal.remove();
    }
}
