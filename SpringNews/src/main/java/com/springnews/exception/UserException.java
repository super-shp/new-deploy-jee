package com.springnews.exception;

public class UserException extends RuntimeException {
    private Integer code;

    public UserException(Integer code, String message){
        super(message);
        this.code = code;
    }

    public Integer getCode(){
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
