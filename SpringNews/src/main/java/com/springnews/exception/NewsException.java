package com.springnews.exception;

import com.springnews.enums.ResultEnum;

public class NewsException extends RuntimeException {

    private int code;

    public NewsException(ResultEnum resultEnum){
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
