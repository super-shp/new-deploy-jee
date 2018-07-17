package com.springnews.utils;

public class ResultUtil {

    public static UnifyResponse successs(Object object){
        UnifyResponse response = new UnifyResponse();
        response.setCode(200);
        response.setMsg("OK");
        response.setData(object);
        return response;
    }

    public static UnifyResponse successs(){
        return successs(null);
    }

    public static UnifyResponse error(Integer code, String msg){
        UnifyResponse response = new UnifyResponse();
        response.setCode(code);
        response.setMsg("OK");
        return response;
    }
}
