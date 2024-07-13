package com.lingfenglong.spzx.log.utils;

import com.alibaba.fastjson.JSON;
import com.lingfenglong.spzx.log.annotation.Log;
import com.lingfenglong.spzx.log.enums.Status;
import com.lingfenglong.spzx.model.entity.system.SysOperLog;
import com.lingfenglong.spzx.util.AuthContextUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class LogUtil {

    public static void beforeProceed(
            SysOperLog sysOperLog,
            ProceedingJoinPoint joinPoint,
            Log sysLog
    ) {
        // 设置操作模块名称
        sysOperLog.setTitle(sysLog.title());
        sysOperLog.setOperatorType(sysLog.operatorType().name());

        // 获取目标方法信息
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature() ;
        Method method = methodSignature.getMethod();
        sysOperLog.setMethod(method.getDeclaringClass().getName());

        // 获取请求相关参数
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        sysOperLog.setRequestMethod(request.getMethod());
        sysOperLog.setOperUrl(request.getRequestURI());
        sysOperLog.setOperIp(request.getRemoteAddr());

        // 设置请求参数
        if(sysLog.isSaveRequestData()) {
            String requestMethod = sysOperLog.getRequestMethod();
            if (HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod)) {
                String params = Arrays.toString(joinPoint.getArgs());
                sysOperLog.setOperParam(params);
            }
        }

        AtomicReference<String> username = new AtomicReference<>();
        Optional.ofNullable(AuthContextUtil.getSysUser())
                        .ifPresentOrElse(
                                sysUser -> username.set(sysUser.getUserName()),
                                () -> username.set(AuthContextUtil.getUserInfo().getUsername())
                        );

        sysOperLog.setOperName(username.get());
    }

    public static void afterProceed(
            SysOperLog sysOperLog,
            Object proceed,
            Log sysLog,
            Status status,
            String message
    ) {
        if(sysLog.isSaveResponseData()) {
            sysOperLog.setJsonResult(JSON.toJSONString(proceed));
        }
        sysOperLog.setStatus(status.code);
        sysOperLog.setErrorMsg(message);
    }
}
