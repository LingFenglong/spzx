package com.lingfenglong.spzx.log.service;

import com.lingfenglong.spzx.model.entity.system.SysOperLog;
import org.springframework.scheduling.annotation.Async;

public interface SysOperLogService {
    @Async
    void saveLog(SysOperLog sysOperLog);
}
