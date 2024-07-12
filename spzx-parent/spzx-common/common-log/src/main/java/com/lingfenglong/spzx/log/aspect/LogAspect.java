package com.lingfenglong.spzx.log.aspect;

import com.lingfenglong.spzx.log.annotation.Log;
import com.lingfenglong.spzx.log.enums.Status;
import com.lingfenglong.spzx.log.service.SysOperLogService;
import com.lingfenglong.spzx.log.utils.LogUtil;
import com.lingfenglong.spzx.model.entity.system.SysOperLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

    private final SysOperLogService sysLogService;

    public LogAspect(SysOperLogService sysLogService) {
        this.sysLogService = sysLogService;
    }

    @Around("@annotation(sysLog)")
    public Object recordLog(ProceedingJoinPoint joinPoint, Log sysLog) {
        SysOperLog sysOperLog = new SysOperLog();
        Object proceed = null;

        LogUtil.beforeProceed(sysOperLog, joinPoint, sysLog);
        try {
            proceed = joinPoint.proceed();
            LogUtil.afterProceed(sysOperLog, proceed, sysLog, Status.OK, null);
            sysLogService.saveLog(sysOperLog);

        } catch (Throwable e) {
            LogUtil.afterProceed(sysOperLog, proceed, sysLog, Status.FAIL, e.getMessage());
            sysLogService.saveLog(sysOperLog);

            throw new RuntimeException(e);
        }
        return proceed;
    }
}
