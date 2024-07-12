package com.lingfenglong.spzx.log.enums;

public enum BusinessType {
    OTHER(0, "其他"),
    INSERT(1, "新增"),
    UPDATE(2, "修改"),
    DELETE(3, "删除"),
    SELECT(4, "查询"),
    NOT_SET(5, "未指定");

    public final int code;
    public final String info;

    BusinessType(int code, String info) {
        this.code = code;
        this.info = info;
    }
}
