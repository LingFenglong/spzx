package com.lingfenglong.spzx.common.exception;

import com.lingfenglong.spzx.model.vo.common.SysUserResultCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserException extends RuntimeException {
    private SysUserResultCode sysUserResultCode;

    public SysUserException(String message, SysUserResultCode sysUserResultCode) {
        super(message);
        this.sysUserResultCode = sysUserResultCode;
    }

}
