package com.springnews.utils;

/**
 * 统一的响应格式
 */
public class UnifyResponse<T> {
    private Integer code;

    private String msg;

    /**
     * 返回的数据
     */
    private T data;

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(T data) {
        this.data = data;
    }
}
