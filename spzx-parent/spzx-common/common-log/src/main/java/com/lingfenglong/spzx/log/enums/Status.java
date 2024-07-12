package com.lingfenglong.spzx.log.enums;

public enum Status {
    OK(0),
    FAIL(1);

    public final int code;

    Status(int code) {
        this.code = code;
    }
}
