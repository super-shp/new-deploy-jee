package com.springnews.utils;

public enum ResponseCode {

    UNKNOW_ERROR(-1, "未知错误"),
    SUCCESS(200, "OK");

    private Integer code;
    private String msg;

    ResponseCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
