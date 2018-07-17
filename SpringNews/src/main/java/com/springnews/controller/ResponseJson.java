package com.springnews.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.springnews.utils.ResponseCode;

public class ResponseJson {
    public String responseJson(int errorCode, String msg, JsonObject data){
        JsonObject json = new JsonObject();
        //addProperty是添加属性（数值、数组等）；add是添加json对象
        json.addProperty("errorCode", errorCode);
        json.addProperty("msg", msg);
        if(data != null){
            json.add("data", data);
        }
        else{
            json.addProperty("data", "null");
        }
        Gson gson = new Gson();
        return gson.toJson(json);
    }

    public String responseJson(ResponseCode res, JsonObject data){
        JsonObject json = new JsonObject();
        //addProperty是添加属性（数值、数组等）；add是添加json对象
        json.addProperty("errorCode", res.getCode());
        json.addProperty("msg", res.getMsg());
        if(data != null){
            json.add("data", data);
        }
        else{
            json.addProperty("data", "null");
        }
        Gson gson = new Gson();
        return gson.toJson(json);
    }
}
