package com.lingfenglong.spzx.common.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.lingfenglong.spzx.common.exception.CommonGlobalRuntimeException;
import com.lingfenglong.spzx.model.entity.user.UserInfo;
import com.lingfenglong.spzx.model.vo.common.SysUserResultCode;
import com.lingfenglong.spzx.util.AuthContextUtil;
import com.lingfenglong.spzx.util.constant.RedisConstant;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;

@Component
public class UserLoginAuthInterceptor implements HandlerInterceptor {

    private final RedisTemplate<String, JSONObject> redisTemplate;

    public UserLoginAuthInterceptor(RedisTemplate<String, JSONObject> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception {
        String token = request.getHeader("token");

        // 无token，直接返回
        if (!StringUtils.hasText(token)) {
            return true;
        }

        UserInfo userInfo = Optional
                .ofNullable((redisTemplate.opsForValue().get(RedisConstant.KEY_USER_TOKEN + token)))
                .orElseThrow(() -> new CommonGlobalRuntimeException(SysUserResultCode.LOGIN_AUTH))
                .toJavaObject(UserInfo.class);

        AuthContextUtil.setUserInfo(userInfo);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        AuthContextUtil.removeUserInfo();
    }
}
