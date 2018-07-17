package com.springnews.exception;

import com.springnews.enums.ResultEnum;

public class UserException extends RuntimeException {
    private Integer code;

    public UserException(Integer code, String message){
        super("UserExcpetion:" + message);
        this.code = code;
    }

    public UserException(ResultEnum resultEnum){
        super("UserException:" + resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public Integer getCode(){
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
