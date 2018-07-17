package com.springnews.utils;

import com.springnews.enums.ResultEnum;

public class ResultUtil {

    public static UnifyResponse successs(ResultEnum resultEnum, Object object){
        UnifyResponse response = new UnifyResponse();
        response.setCode(resultEnum.getCode());
        response.setMsg(resultEnum.getMsg());
        response.setData(object);
        return response;
    }

    public static UnifyResponse successs(ResultEnum resultEnum){
        return successs(resultEnum,null);
    }

    public static UnifyResponse error(Integer code, String msg){
        UnifyResponse response = new UnifyResponse();
        response.setCode(code);
        response.setMsg(msg);
        return response;
    }
}
