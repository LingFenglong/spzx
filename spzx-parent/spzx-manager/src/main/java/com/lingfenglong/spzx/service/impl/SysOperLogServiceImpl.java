package com.lingfenglong.spzx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingfenglong.spzx.mapperplus.SysOperLogMapper;
import com.lingfenglong.spzx.model.entity.system.SysOperLog;
import com.lingfenglong.spzx.service.SysOperLogService;
import org.springframework.stereotype.Service;

@Service
public class SysOperLogServiceImpl
        extends ServiceImpl<SysOperLogMapper, SysOperLog>
        implements SysOperLogService {

    @Override
    public void saveLog(SysOperLog sysOperLog) {
        save(sysOperLog);
    }
}
