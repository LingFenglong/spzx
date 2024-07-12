package com.lingfenglong.spzx.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public enum ProductAuditStatus {
    NOT_PROCESSED(0, "未处理"),
    NOT_PASSED(-1, "未通过"),
    PASSED(1, "已通过");


    private static final Map<Integer, ProductAuditStatus> map =
            Arrays.stream(ProductAuditStatus.values())
                    .collect(
                            Collectors.toMap(ProductAuditStatus::getCode,
                            Function.identity())
                    );

    private final Integer code;
    private final String message;

    ProductAuditStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ProductAuditStatus getProductAuditStatus(Integer code) {
        return map.get(code);
    }
}
