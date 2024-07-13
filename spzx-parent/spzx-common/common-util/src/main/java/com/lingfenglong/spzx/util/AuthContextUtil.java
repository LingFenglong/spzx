package com.lingfenglong.spzx.util;

import com.lingfenglong.spzx.model.entity.system.SysUser;
import com.lingfenglong.spzx.model.entity.user.UserInfo;
import lombok.Data;

/**
 * 将用户信息存储到ThreadLocal的工具类
 */

@Data
public class AuthContextUtil {
    private static final ThreadLocal<SysUser> sysUserThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<UserInfo> userInfoThreadLocal = new ThreadLocal<>();

    public static void setSysUser(SysUser sysUser) {
        sysUserThreadLocal.set(sysUser);
    }

    public static SysUser getSysUser() {
        return sysUserThreadLocal.get();
    }

    public static void removeSysUser() {
        sysUserThreadLocal.remove();
    }

    public static void setUserInfo(UserInfo userInfo) {
        userInfoThreadLocal.set(userInfo);
    }

    public static UserInfo getUserInfo() {
        return userInfoThreadLocal.get();
    }

    public static void removeUserInfo() {
        userInfoThreadLocal.remove();
    }
}
