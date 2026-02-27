package com.aim.mall.common.api;

import lombok.Getter;

/**
 * 枚举了一些常用API操作码
 */
@Getter
public enum ResultCode implements IErrorCode {
    SUCCESS(200, "操作成功");

    private final long code;
    private final String message;

    private ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    public long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
