package com.lingfenglong.spzx.interceptor;

import com.alibaba.fastjson.JSON;
import com.lingfenglong.spzx.common.exception.SysUserException;
import com.lingfenglong.spzx.model.entity.system.SysUser;
import com.lingfenglong.spzx.model.vo.common.SysUserResultCode;
import com.lingfenglong.spzx.util.AuthContextUtil;
import com.lingfenglong.spzx.util.RedisPrefix;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.concurrent.TimeUnit;

@Component
public class LoginAuthInterceptor implements HandlerInterceptor {
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1.请求方法是否为OPTIONS，是直接放行
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }

        // 2.从header中获得token值，如果没有，返回(code 401)，需要登录
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            throw new SysUserException("用户未登录", SysUserResultCode.LOGIN_AUTH);
        }

        // 3.从redis中查询token，如果没有，返回(code 401)，需要登录
        String sysUserInfo = stringRedisTemplate.opsForValue().get(RedisPrefix.USER_LOGIN + token);
        if (sysUserInfo == null) {
            throw new SysUserException("用户未登录", SysUserResultCode.LOGIN_AUTH);
        }

        // 4.延长token过期时间
        // stringRedisTemplate.expire(RedisPrefix.USER_LOGIN + token, 30, TimeUnit.MINUTES);
        stringRedisTemplate.expire(RedisPrefix.USER_LOGIN + token, 30000, TimeUnit.DAYS);

        // 5.token存在，获得用户信息，存入ThreadLocal中
        SysUser sysUser = JSON.parseObject(sysUserInfo, SysUser.class);
        AuthContextUtil.setSysUser(sysUser);

        // 6.返回通过
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        AuthContextUtil.removeSysUser();
    }

    @Autowired
    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }
}
