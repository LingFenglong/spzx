package com.lingfenglong.spzx.model.vo.common;

import lombok.Getter;

@Getter
public enum CommonResultCode implements ResultCode {
    SUCCESS(200, "操作成功"),
    SYSTEM_ERROR(9999, "您的网络有问题请稍后重试"),
    NODE_ERROR(217, "该节点下有子节点，不可以删除"),
    DATA_ERROR(204, "数据异常"),
    STOCK_LESS(219, "库存不足"),
    // GlobalException
    UNKNOWN_GLOBAL_EXCEPTION(804, "未知的全局异常");

    private final Integer code;      // 业务状态码
    private final String message;    // 响应消息

    CommonResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
