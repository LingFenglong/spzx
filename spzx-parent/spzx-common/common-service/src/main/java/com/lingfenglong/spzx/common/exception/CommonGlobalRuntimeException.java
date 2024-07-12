package com.lingfenglong.spzx.common.exception;

import com.lingfenglong.spzx.model.vo.common.CommonResultCode;
import com.lingfenglong.spzx.model.vo.common.ResultCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CommonGlobalRuntimeException extends RuntimeException {

    public CommonGlobalRuntimeException(ResultCode resultCode) {
        super(resultCode.getMessage());
    }
}
