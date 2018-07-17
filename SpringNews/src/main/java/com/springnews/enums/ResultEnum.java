package com.springnews.enums;

public enum ResultEnum {
    OK(200,"OK"),
    SUCCESS(201,"success"),
    OPTION_FAILED(100, "option failed"),
    UNKNOWN_FAILED(999, "unknown failed")
    ;
    private int code;
    private String msg;

    ResultEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
